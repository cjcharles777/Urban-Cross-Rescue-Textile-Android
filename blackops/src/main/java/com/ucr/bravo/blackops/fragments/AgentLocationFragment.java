package com.ucr.bravo.blackops.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.reflect.TypeToken;
import com.ucr.bravo.blackops.BlackOpsApplication;
import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.listeners.AppLocationListener;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.object.beans.AgentLocation;
import com.ucr.bravo.blackops.rest.object.beans.LocationBounds;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;
import com.ucr.bravo.blackops.rest.service.AgentService;
import com.ucr.bravo.blackops.rest.utils.JsonResponseConversionUtil;
import com.ucr.bravo.blackops.rest.utils.NetworkCommunicationUtil;
import com.ucr.bravo.blackops.utils.LocationUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class AgentLocationFragment extends Fragment {

    private View rootView;
    private MapView mapView;
    private GoogleMap map;

    private AppLocationListener mCallback;
    private AgentService agentService = new AgentService();
    Handler handler;
    Runnable runnable;
    HashMap<String, Marker> markerHashMap = new HashMap<String, Marker>();



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgentLocationFragment.
     */

    public static AgentLocationFragment newInstance(String param1, String param2) {
        AgentLocationFragment fragment = new AgentLocationFragment();

        return fragment;
    }
    public AgentLocationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_agent_location, container, false);
        setHasOptionsMenu(true);
        return rootView;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) rootView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        initMap();




        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        Location loc = mCallback.getCurrentLocation();
        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(),loc.getLongitude()), 10);
        map.animateCamera(cameraUpdate);

        getAgentLocations();
        startLocationUpdater();
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        changeLocationIcon(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_toggle_location:
                toggleLocation();
                getActivity().invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeLocationIcon(Menu menu)
    {
        String onOffString = (isLocationSharing())?getActivity().getString(R.string.on_text):getActivity().getString(R.string.off_text);
        int onOffIcon =  (isLocationSharing())?R.drawable.ic_action_location_found:R.drawable.ic_action_location_off;
        MenuItem item = menu.add(Menu.NONE, R.id.action_toggle_location, 10, onOffString);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setIcon(onOffIcon);
    }
    private void toggleLocation()
    {
        boolean mUpdatesRequested;
        mUpdatesRequested = !isLocationSharing();
        Context context = getActivity();
        final SharedPreferences sharedPref = context.getSharedPreferences(LocationUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();// Todo: move to Location activity something like toggle UpdateRequested
        editor.putBoolean("KEY_UPDATES_ON", mUpdatesRequested);
        editor.commit();
        if(mUpdatesRequested)
        {
            mCallback.startUpdates();
        }
        else
        {
            mCallback.stopUpdates();
        }
    }
    private boolean isLocationSharing()
    {
        boolean mUpdatesRequested;

        Context context = getActivity();
        final SharedPreferences sharedPref = context.getSharedPreferences(LocationUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPref.contains("KEY_UPDATES_ON"))
        {
            mUpdatesRequested =
                    sharedPref.getBoolean("KEY_UPDATES_ON", false);

            // Otherwise, turn off location updates
        }
        else
        {
            mUpdatesRequested = false;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("KEY_UPDATES_ON", mUpdatesRequested);
            editor.commit();
        }
        return mUpdatesRequested;
    }

    private void getAgentLocations()
    {
        LatLngBounds latLngBounds = map.getProjection().getVisibleRegion().latLngBounds;
        final LocationBounds locationBounds = new LocationBounds(new BigDecimal(latLngBounds.northeast.latitude),
                new BigDecimal(latLngBounds.southwest.latitude),
                new BigDecimal(latLngBounds.northeast.longitude),
                new BigDecimal(latLngBounds.southwest.longitude));
        final BaseRestPostAction baseRestPostAction = new BaseRestPostAction(this.getActivity())
        {
            @Override
            public void onSuccess(BaseResponse response)
            {
                List<AgentLocation> results = (List<AgentLocation>) JsonResponseConversionUtil.convertMessageToObjectList(response.getMessage(),
                        new TypeToken<List<AgentLocation>>(){});
                insertIntoMap(results);
                Log.d(LocationUtils.APPTAG, "Grabbed Agent Locations");
                //startActivity(intent);

            }
        };
        NetworkCommunicationUtil network = new NetworkCommunicationUtil()
        {
            @Override
            protected void runTask()
            {
                agentService.retrieveAgentLocation(baseRestPostAction, locationBounds, ((BlackOpsApplication) getActivity().getApplication()).getRequesterId());
            }
        };
        network.processNetworkTask(getActivity());
    }
    private void insertIntoMap(List<AgentLocation> results)
    {
       for(AgentLocation result : results)
       {
           Marker existingMarker = markerHashMap.get(result.getAgent().getId());
           boolean isUpdate = (existingMarker == null ||
                    existingMarker.getPosition().latitude != result.getLatitude().doubleValue() ||
                    existingMarker.getPosition().latitude != result.getLongitude().doubleValue());
           if(isUpdate)
           {
               if(existingMarker != null)
               {
                   existingMarker.remove();
               }

               Marker updatedMarker = map.addMarker(new MarkerOptions()
                       .position(new LatLng(result.getLatitude().doubleValue(),
                               result.getLongitude().doubleValue()))
                       .title(result.getAgent().getIgn())
                       .snippet("Last Updated : " + DateUtils.getRelativeTimeSpanString(result.getUpdated().getTime(), (new Date()).getTime(), DateUtils.MINUTE_IN_MILLIS))
                       .icon(BitmapDescriptorFactory.fromResource(R.drawable.revolt)));
               markerHashMap.put(result.getAgent().getId(),updatedMarker);
           }


       }

    }

    private void startLocationUpdater()
    {
        handler = new Handler();


        runnable = new Runnable()
        {

            public void run()
            {
                getAgentLocations();

                handler.postDelayed(this, 120000);
            }
        };
        runnable.run();

    }
    private void initMap()
    {
        UiSettings settings = map.getUiSettings();
        settings.setAllGesturesEnabled(true);
        settings.setMyLocationButtonEnabled(true);

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(handler != null && runnable != null)
        {
            handler.removeCallbacks(runnable);
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try
        {
            mCallback = (AppLocationListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement AppLocationListener");
        }
    }




}
