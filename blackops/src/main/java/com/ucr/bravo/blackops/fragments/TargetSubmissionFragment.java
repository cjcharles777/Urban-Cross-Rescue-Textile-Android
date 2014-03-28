package com.ucr.bravo.blackops.fragments;

import android.app.Activity;
import android.app.ListFragment;
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
import android.widget.Toast;

import com.ucr.bravo.blackops.BlackOpsApplication;
import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.adapters.PortalSearchArrayAdapter;
import com.ucr.bravo.blackops.rest.object.beans.Portal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cedric on 3/14/14.
 */
public class TargetSubmissionFragment extends Fragment
{
    private AutoCompleteTextView actv;
    ArrayAdapter<String> adapter;
    ArrayList<Portal> listPortal;
    Portal selected;
    OnAddPortalsListener mCallback;
    public static final String ARG_PORTAL_LIST = "PORTAL_LIST";

    // Container Activity must implement this interface
    public interface OnAddPortalsListener
    {
        public void onAddButtonPressed(List<Portal> pList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_target_submission, container, false);



        Button button = (Button) rootView.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                mCallback.onAddButtonPressed(listPortal);
            }
        });

        return rootView;
    }


    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try
        {
            mCallback = (OnAddPortalsListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAddPortalsListener");
        }
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
            listPortal = (args.getParcelableArrayList(ARG_PORTAL_LIST));
        }

    }

    public void setUIArguments(Bundle args)
    {
        if (args != null) {
            // Set article based on argument passed in
            listPortal = (args.getParcelableArrayList(ARG_PORTAL_LIST));
        }
    }

}
