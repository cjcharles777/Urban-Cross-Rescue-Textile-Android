package com.ucr.bravo.blackops.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.ucr.bravo.blackops.BlackOpsApplication;
import com.ucr.bravo.blackops.R;
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
public class JobListFragment extends ListFragment
{
    private ListView listView;
   // private ArrayList<Menu> menuItems;
    private TargetListArrayAdapter mAdapter;
    private JobService jobService;
    private Agent agent;

    static final String[] MOBILE_OS =
            new String[] { "Android", "iOS", "WindowsMobile", "Blackberry"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_job_table, container, false);

        listView = (ListView) rootView.findViewById(android.R.id.list);
        jobService = new JobService();

        agent = ((BlackOpsApplication) getActivity().getApplication()).getSessionAgent();
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
                    mAdapter = new TargetListArrayAdapter(getActivity(), android.R.id.list, results);
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
    public void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
        //Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

    }
}
