package com.ucr.bravo.blackops.fragments;

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

/**
 * Created by cedric on 3/27/14.
 */
public class PortalListSelectionFragment extends Fragment
{


    public static final String ARG_PORTAL_LIST = "PORTAL_LIST";
    private AutoCompleteTextView actv;
    Portal selected;
    ArrayList<Portal> listPortal = new ArrayList<Portal>();
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

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
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listItems);
        ListView listview = (ListView) rootView.findViewById(android.R.id.list);
        listview.setAdapter(adapter);

        Button button = (Button) rootView.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(selected != null)
                {
                    listItems.add(selected.getName());
                    adapter.notifyDataSetChanged();
                    actv.setText("");
                    selected = null;
                }
            }
        });

        return rootView;
    }
    public void setListPortal(ArrayList<Portal> pList)
    {
        listPortal =  pList;

    }
}
