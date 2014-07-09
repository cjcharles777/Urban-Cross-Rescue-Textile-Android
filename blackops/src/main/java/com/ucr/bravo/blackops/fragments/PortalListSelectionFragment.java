package com.ucr.bravo.blackops.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.ucr.bravo.blackops.BlackOpsApplication;
import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.activities.LocationActivity;
import com.ucr.bravo.blackops.adapters.PortalListArrayAdapter;
import com.ucr.bravo.blackops.adapters.PortalSearchArrayAdapter;
import com.ucr.bravo.blackops.listeners.PortalListListener;
import com.ucr.bravo.blackops.rest.object.beans.Portal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cedric on 3/27/14.
 */
public class PortalListSelectionFragment extends Fragment
{



    private AutoCompleteTextView actv;
    private View rootView;
    Portal selected;
    List<Portal> listPortal = new ArrayList<Portal>();
    //ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<Portal> adapter;
    private PortalListListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.





        rootView = inflater.inflate(R.layout.fragment_portal_selection_list, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {

        super.onActivityCreated(savedInstanceState);
        listPortal = mCallback.retrieveCurrentPortalList();
        actv = (AutoCompleteTextView) rootView.findViewById(R.id.portalAutoCompleteTextView);
        actv.setAdapter(new PortalSearchArrayAdapter(getActivity(), ((BlackOpsApplication) getActivity().getApplication()).getRequesterId()));
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3)
            {
                selected = (Portal) arg0.getAdapter().getItem(arg2);
                //listItems.add(selected.getName());
                // adapter.notifyDataSetChanged();
            }
        });

        adapter= new PortalListArrayAdapter(getActivity(), android.R.id.list, listPortal, (LocationActivity)getActivity(), true);
        ListView listview = (ListView) rootView.findViewById(android.R.id.list);
        listview.setAdapter(adapter);

        Button button = (Button) rootView.findViewById(R.id.viewPortalsButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(selected != null)
                {
                    addPortal();
                }
            }
        });

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.add(Menu.NONE, R.id.action_submit_portal_list, 10, R.string.submit_portal_list_text);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setIcon(R.drawable.ic_action_save);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_submit_portal_list:
                mCallback.onSubmitPortalsListButtonPressed(listPortal);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void addPortal()
    {
        listPortal.add(selected);
        adapter.notifyDataSetChanged();
        actv.setText("");
        selected = null;
    }
    private void removePortal()
    {

    }
    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point .
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            if(listPortal == null)
            {
                listPortal = new ArrayList<Portal>();
            }
            listPortal.clear();
            List<Portal> temp = mCallback.retrieveCurrentPortalList();
           if(temp != null)
           {
               listPortal.addAll(temp);
               adapter.notifyDataSetChanged();
           }
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        //outState.putParcelableArrayList(ARG_PORTAL_LIST, listPortal);
    }
    // Container Activity must implement this interface
    public interface PortalListSelectionFragmentListener
    {
        public void onSubmitPortalsListButtonPressed(List<Portal> pList);
        public ArrayList<Portal> retrieveCurrentPortalList();

    }
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try
        {
            mCallback = (PortalListListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAddPortalsListener");
        }
    }

    public List<Portal> getListPortal() {
        return listPortal;
    }

    public void setListPortal(List<Portal> listPortal) {
        this.listPortal = listPortal;
    }
}
