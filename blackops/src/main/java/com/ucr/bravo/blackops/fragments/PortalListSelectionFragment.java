package com.ucr.bravo.blackops.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.ucr.bravo.blackops.BlackOpsApplication;
import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.adapters.PortalSearchArrayAdapter;
import com.ucr.bravo.blackops.rest.object.beans.Portal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cedric on 3/27/14.
 */
public class PortalListSelectionFragment extends Fragment
{



    private AutoCompleteTextView actv;
    Portal selected;
    ArrayList<Portal> listPortal = new ArrayList<Portal>();
    //ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<Portal> adapter;
    PortalListSelectionFragmentListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.

            listPortal = mCallback.retrieveCurrentPortalList();



        View rootView = inflater.inflate(R.layout.fragment_portal_selection_list, container, false);

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
        adapter=new ArrayAdapter<Portal>(getActivity(),android.R.layout.simple_list_item_1,listPortal);
        ListView listview = (ListView) rootView.findViewById(android.R.id.list);
        listview.setAdapter(adapter);

        Button button = (Button) rootView.findViewById(R.id.viewPortalsButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(selected != null)
                {
                    listPortal.add(selected);
                    adapter.notifyDataSetChanged();
                    actv.setText("");
                    selected = null;
                }
            }
        });
        Button button2 = (Button) rootView.findViewById(R.id.submitPortalListButton);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                    mCallback.onSubmitPortalsListButtonPressed(listPortal);
            }
        });

        return rootView;
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
                new ArrayList<Portal>();
            }
            listPortal.clear();
            List<Portal> temp = mCallback.retrieveCurrentPortalList();;
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
            mCallback = (PortalListSelectionFragmentListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAddPortalsListener");
        }
    }

}
