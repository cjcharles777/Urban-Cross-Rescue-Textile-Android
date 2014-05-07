package com.ucr.bravo.blackops.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.ucr.bravo.blackops.R;

import com.ucr.bravo.blackops.activities.LocationActivity;
import com.ucr.bravo.blackops.adapters.PortalListArrayAdapter;
import com.ucr.bravo.blackops.fragments.dummy.DummyContent;
import com.ucr.bravo.blackops.rest.object.beans.Portal;

import java.util.ArrayList;
import java.util.List;

public class PortalListReviewFragment extends Fragment implements AbsListView.OnItemClickListener {

    private ArrayList<Portal> listPortal = new ArrayList<Portal>();
    private ArrayAdapter<Portal> adapter;


    private PortalListReviewFragmentListener mCallback;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static PortalListReviewFragment newInstance(String param1, String param2) {
        PortalListReviewFragment fragment = new PortalListReviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PortalListReviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.

        listPortal = (ArrayList<Portal>)mCallback.retrieveCurrentPortalList();
        View view = inflater.inflate(R.layout.fragment_portal, container, false);
        adapter= new PortalListArrayAdapter(getActivity(), android.R.id.list, listPortal, (LocationActivity)getActivity());
        ListView listview = (ListView) view.findViewById(android.R.id.list);
        listview.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            mCallback = (PortalListReviewFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mCallback) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
           // mCallback.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */
    public interface PortalListReviewFragmentListener {
        // TODO: Update argument type and name
        public List<Portal> retrieveCurrentPortalList();
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
}
