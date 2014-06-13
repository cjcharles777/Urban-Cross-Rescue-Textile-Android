package com.ucr.bravo.blackops.fragments;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.ucr.bravo.blackops.BlackOpsApplication;
import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.activities.LocationActivity;
import com.ucr.bravo.blackops.activities.MainActivity;
import com.ucr.bravo.blackops.adapters.TargetListArrayAdapter;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.object.beans.Agent;
import com.ucr.bravo.blackops.rest.object.beans.Job;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;
import com.ucr.bravo.blackops.rest.service.JobService;
import com.ucr.bravo.blackops.rest.utils.JsonResponseConversionUtil;

import java.util.List;

/**
 * Created by cedric on 3/5/14.
 */
public class JobListFragment extends ListFragment implements LocationActivity.OnLocationUpdatedListener
{
    private ListView listView;
   // private ArrayList<Menu> menuItems;
    private TargetListArrayAdapter mAdapter;
    private JobService jobService;
    private Agent agent;
    private JobListFragmentListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_job_table, container, false);

        listView = (ListView) rootView.findViewById(android.R.id.list);
        jobService = new JobService();

        agent = ((BlackOpsApplication) getActivity().getApplication()).getSessionAgent();
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        BaseRestPostAction baseRestPostAction = new BaseRestPostAction()
        {
            @Override
            public void onPostExecution(String str) {
                BaseResponse response = JsonResponseConversionUtil.convertToResponse(str);
                if(response.getResult().equals("SUCCESS"))
                {
                    List results;
                    results = (List<Job>) JsonResponseConversionUtil.convertMessageToObjectList(response.getMessage(), new TypeToken<List<Job>>(){});
                    mAdapter = new TargetListArrayAdapter(getActivity(), android.R.id.list, results, (LocationActivity)getActivity());
                    listView.setAdapter(mAdapter);
                }
                else
                {
                    //Todo : What should be implemented upon failure.
                }

            }
        };

        jobService.retrieveJobs(baseRestPostAction, new Job(), agent.getId());


    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {

        //get selected items
        Job selectedValue = (Job) l.getAdapter().getItem(position);
        mCallback.onListItemClick(selectedValue);
        //Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_submit:

                Fragment fragment = new JobSubmissionFragment();
                ((MainActivity) getActivity()).switchFragments(fragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onLocationUpdated(Location location)
    {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item =
                menu.add(Menu.NONE, R.id.action_submit, 10,R.string.submit_target);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setIcon(R.drawable.ic_action_new);

    }

    // Container Activity must implement this interface
    public interface JobListFragmentListener
    {
        public void onListItemClick(Job job);

    }
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try
        {
            mCallback = (JobListFragmentListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement JobListFragmentListener");
        }
    }
}
