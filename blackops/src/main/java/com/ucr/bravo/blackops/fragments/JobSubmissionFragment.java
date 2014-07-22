package com.ucr.bravo.blackops.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucr.bravo.blackops.BlackOpsApplication;
import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.activities.MainActivity;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.object.beans.Job;
import com.ucr.bravo.blackops.rest.object.beans.Portal;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;
import com.ucr.bravo.blackops.rest.service.JobService;
import com.ucr.bravo.blackops.rest.utils.NetworkCommunicationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cedric on 3/14/14.
 */
public class JobSubmissionFragment extends BasePortalListFragment
{


    OnAddPortalsListener mCallback;
    private JobService jobService = new JobService();
    private View rootView;
    private Job job;

    public static final String ARG_PORTAL_LIST = "PORTAL_LIST";

    // Container Activity must implement this interface
    public interface OnAddPortalsListener
    {
        public void onAddButtonPressed(List<Portal> pList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_job_submission, container, false);
        setHasOptionsMenu(true);

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if(job != null)
        {

            if(job.getTargets() != null && getListPortal() == null)
            {
                setListPortal(job.getTargets());
            }
            TextView detailTxt = (TextView) rootView.findViewById(R.id.jobDetailsTextView);
            TextView titleTxt = (TextView) rootView.findViewById(R.id.headlineEditText);
            detailTxt.setText(job.getDetails());
            titleTxt.setText(job.getTitle());
        }
        else
        {
            job = new Job();
            //job.setTargets(listPortal);
        }
        if(getListPortal() == null)
        {

            setListPortal(new ArrayList<Portal>());
        }



        ImageView addButton = (ImageView) rootView.findViewById(R.id.viewPortalsButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCallback.onAddButtonPressed(getListPortal());
            }
        });

        TextView numOfPortalsTextView = (TextView) rootView.findViewById(R.id.numOfPortalsTextView);
        numOfPortalsTextView.setText(getListPortal().size() + "Portals");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.add(Menu.NONE, R.id.action_submit_job, 10, R.string.submit_job_text);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setIcon(R.drawable.ic_action_save);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_submit_job:
                submitJob();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void submitJob()
    {
        TextView detailTxt = (TextView) rootView.findViewById(R.id.jobDetailsTextView);
        TextView titleTxt = (TextView) rootView.findViewById(R.id.headlineEditText);
        job.setTargets(getListPortal());
        job.setTitle(titleTxt.getText().toString());
        job.setDetails(detailTxt.getText().toString());
        final BaseRestPostAction baseRestPostAction = new BaseRestPostAction(this.getActivity())
        {

            @Override
            public void onSuccess(BaseResponse response)
            {
                MainActivity main = ((MainActivity) getActivity());
                main.selectItem(2);
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
            //listPortal = (args.getParcelableArrayList(ARG_PORTAL_LIST));
        }

    }


    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
