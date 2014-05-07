package com.ucr.bravo.blackops.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.ucr.bravo.blackops.BlackOpsApplication;
import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.activities.LocationActivity;
import com.ucr.bravo.blackops.adapters.TargetListArrayAdapter;
import com.ucr.bravo.blackops.adapters.UserSelectListArrayAdapter;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.object.beans.Agent;
import com.ucr.bravo.blackops.rest.object.beans.Job;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;
import com.ucr.bravo.blackops.rest.service.AgentService;
import com.ucr.bravo.blackops.rest.utils.JsonResponseConversionUtil;

import java.util.List;

/**
 * Created by cedric on 5/7/14.
 */
public class PendingUserAcceptanceFragment extends Fragment
{
    private ArrayAdapter<Agent> adapter;
    private ListView listView;
    private Agent reqAgent;
    AgentService agentService = new AgentService();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_pending_user_accept, container, false);
        listView = (ListView) rootView.findViewById(android.R.id.list);
        reqAgent = ((BlackOpsApplication) getActivity().getApplication()).getSessionAgent();
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
                    results = (List<Agent>) JsonResponseConversionUtil.convertMessageToObjectList(response.getMessage(), new TypeToken<List<Agent>>(){});
                    adapter = new UserSelectListArrayAdapter(getActivity(), android.R.id.list, results);
                    listView.setAdapter(adapter);
                }
                else
                {
                    //Todo : What should be implemented upon failure.
                }

            }
        };
        Agent a = new Agent();
        a.setAuthorized(false);
        agentService.retrieveAgentByExample(baseRestPostAction, a, reqAgent.getId());


    }
}
