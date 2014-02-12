package com.ucr.bravo.blackops.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.activities.MainActivity;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.object.Agent;
import com.ucr.bravo.blackops.rest.service.AgentService;

/**
 * Created by cedric on 2/7/14.
 */
public class AccessRequestFragment extends Fragment
{
    private View rootView;
    private AgentService agentService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_access_request, container, false);
        Intent intent = getActivity().getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView emailTxt = (TextView)rootView.findViewById(R.id.emailText);
        emailTxt.setText(message);
        agentService = new AgentService();
        Button mButton = (Button) rootView.findViewById(R.id.requestAccessButton);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected())
                {
                    TextView emailTxt = (TextView)rootView.findViewById(R.id.emailText);
                    TextView ignTxt = (TextView)rootView.findViewById(R.id.ignEditText);
                    Agent agent = new Agent();
                    agent.setEmail(emailTxt.getText().toString());
                    agent.setIgn(ignTxt.getText().toString());
                    BaseRestPostAction baseRestPostAction = new BaseRestPostAction() {
                        @Override
                        public void onPostExecution(String str) {
                            Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
                        }
                    };
                    agentService.requestAuthorization(baseRestPostAction, agent);
                }
                else
                {
                    // display error
                }
            }
        });

        return rootView;
    }

}
