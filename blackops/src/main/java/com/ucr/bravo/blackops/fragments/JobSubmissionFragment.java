package com.ucr.bravo.blackops.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ucr.bravo.blackops.BlackOpsApplication;
import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.activities.JobListActivity;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.object.beans.Job;
import com.ucr.bravo.blackops.rest.object.beans.Portal;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;
import com.ucr.bravo.blackops.rest.service.JobService;
import com.ucr.bravo.blackops.rest.utils.JsonResponseConversionUtil;
import com.ucr.bravo.blackops.rest.utils.NetworkCommunicationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cedric on 3/14/14.
 */
public class JobSubmissionFragment extends Fragment
{

    ArrayList<Portal> listPortal;
    OnAddPortalsListener mCallback;
    private JobService jobService = new JobService();
    private View rootView;

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

        if(listPortal == null)
        {
            listPortal = new ArrayList<Portal>();
        }
        rootView = inflater.inflate(R.layout.fragment_job_submission, container, false);


        Button addButton = (Button) rootView.findViewById(R.id.viewPortalsButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCallback.onAddButtonPressed(listPortal);
            }
        });

        Button submitButton = (Button) rootView.findViewById(R.id.acceptJobButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitJob();
            }
        });
        return rootView;
    }

    private void submitJob()
    {
        TextView detailTxt = (TextView) rootView.findViewById(R.id.jobDetailsTextView);
        TextView titleTxt = (TextView) rootView.findViewById(R.id.headlineEditText);
        final Job job = new Job();
        job.setTargets(listPortal);
        job.setTitle(titleTxt.getText().toString());
        job.setDetails(detailTxt.getText().toString());
        final BaseRestPostAction baseRestPostAction = new BaseRestPostAction()
        {
            @Override
            public void onPostExecution(String str) {
                BaseResponse response = JsonResponseConversionUtil.convertToResponse(str);
                if(response.getResult().equals("SUCCESS")) 
                {
                    Intent intent = new Intent(getActivity(), JobListActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getActivity(),getString(R.string.request_error), Toast.LENGTH_LONG).show();
                }

            }
        };

        NetworkCommunicationUtil network = new NetworkCommunicationUtil()
        {
            @Override
            protected void runTask()
            {
                jobService.submit(baseRestPostAction, job, ((BlackOpsApplication) getActivity().getApplication()).getRequesterId());
            }
        };
        network.processNetworkTask(getActivity());
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

   public ArrayList<Portal> getListPortal()
   {
       return listPortal;
   }

    public void setListPortal(ArrayList<Portal> listPortal)
    {
        this.listPortal = listPortal;
    }

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

}