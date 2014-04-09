package com.ucr.bravo.blackops.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.rest.object.beans.Portal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cedric on 4/9/14.
 */
public class PortalListMapFragment extends SupportMapFragment
{
    private List<Portal> pList;
    private PortalListReviewFragment.PortalListReviewFragmentListener mCallback;
    private GoogleMap gMap;

    public static PortalListMapFragment newInstance(List<Portal> pList){
        PortalListMapFragment frag = new PortalListMapFragment();
        frag.pList = pList;

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
        pList = (ArrayList<Portal>)mCallback.retrieveCurrentPortalList();
        View v = super.onCreateView(arg0, arg1, arg2);
        GoogleMap gMap = getMap();
        initMap();
        gMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                initCamera();
            }
        });
        return v;
    }

    private void initMap()
    {
        UiSettings settings = getMap().getUiSettings();
        settings.setAllGesturesEnabled(true);
        settings.setMyLocationButtonEnabled(true);

    }
    private void initCamera()
    {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Portal p : pList)
        {
            LatLng pLatLng = new LatLng(p.getLatitude().doubleValue(),p.getLongitude().doubleValue());
            getMap().addMarker(new MarkerOptions()
                    .position(pLatLng)
                    .title(p.getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.resistance_map_marker)));
            builder.include(pLatLng);
        }

        LatLngBounds bounds = builder.build();
        int padding = 10; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        getMap().animateCamera(cu);
    }
    @Override
    public void onStart()
    {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point .
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            if(pList == null)
            {
                new ArrayList<Portal>();
            }
            pList.clear();
            List<Portal> temp = mCallback.retrieveCurrentPortalList();;
            if(temp != null)
            {
                pList.addAll(temp);
                //adapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (PortalListReviewFragment.PortalListReviewFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }


    }


}
