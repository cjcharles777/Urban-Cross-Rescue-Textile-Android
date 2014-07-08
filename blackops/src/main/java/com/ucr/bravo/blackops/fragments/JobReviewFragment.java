package com.ucr.bravo.blackops.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ucr.bravo.blackops.BlackOpsApplication;
import com.ucr.bravo.blackops.R;

import com.ucr.bravo.blackops.activities.MainActivity;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.object.beans.Agent;
import com.ucr.bravo.blackops.rest.object.beans.Job;
import com.ucr.bravo.blackops.rest.object.beans.Portal;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;
import com.ucr.bravo.blackops.rest.service.JobService;
import com.ucr.bravo.blackops.rest.utils.JsonResponseConversionUtil;
import com.ucr.bravo.blackops.rest.utils.NetworkCommunicationUtil;

import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JobReviewFragment.JobReviewFragmentListener} interface
 * to handle interaction events.
 * Use the {@link JobReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class JobReviewFragment extends BasePortalListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private Job job;
    private JobService jobService = new JobService();
    private View rootView;

    private JobReviewFragmentListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobReviewFragment.
     */

    public static JobReviewFragment newInstance(String param1, String param2) {
        JobReviewFragment fragment = new JobReviewFragment();
        return fragment;
    }
    public JobReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_job_review, container, false);
        setHasOptionsMenu(true);

        return rootView;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        TextView jobHeadlineTextView = (TextView) rootView.findViewById(R.id.jobHeadlineTextView);
        TextView numOfPortalstextView = (TextView) rootView.findViewById(R.id.numOfPortalsTextView);
        TextView jobDetailsTextView  = (TextView) rootView.findViewById(R.id.jobDetailsTextView);
        TextView jobRequesterTextView  = (TextView) rootView.findViewById(R.id.jobRequesterTextView);

        Button viewListButton = (Button) rootView.findViewById(R.id.viewPortalsButton);
        viewListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onViewListButtonPressed();
            }
        });
        Button viewMapbutton = (Button) rootView.findViewById(R.id.viewMapbutton);
        viewMapbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onViewMapButtonPressed();
            }
        });
        Button acceptJobButton = (Button) rootView.findViewById(R.id.acceptJobButton);
        acceptJobButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                acceptJob();
            }
        });
        jobHeadlineTextView.setText(job.getTitle());
        List<Portal> portalList = job.getTargets();
        if(portalList != null)
        {
            numOfPortalstextView.setText(portalList.size() +" Portals Selected");
        }
        else
        {
            numOfPortalstextView.setText("0 Portals Selected");
        }
        jobDetailsTextView.setText(job.getDetails());
        Agent requester = job.getRequester();
        if(requester != null)
        {
            jobRequesterTextView.setText(requester.getIgn());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit:


                JobSubmissionFragment fragment = new JobSubmissionFragment();
                fragment.setJob(job);
                ((MainActivity) getActivity()).switchFragments(fragment, true, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        final MainActivity main = (MainActivity) getActivity();
        Agent agent = ((BlackOpsApplication) main.getApplication()).getSessionAgent();
        if(job.getRequester().getId().equals(agent.getId()))
        {
            MenuItem item =
                    menu.add(Menu.NONE, R.id.action_edit, 10, R.string.edit_target);
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            item.setIcon(R.drawable.ic_action_edit);
        }

    }
    private void acceptJob()
    {



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
                jobService.accept(baseRestPostAction, job, ((BlackOpsApplication) getActivity().getApplication()).getRequesterId());
            }
        };
        network.processNetworkTask(getActivity());
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            mListener = (JobReviewFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
        if(job != null)
        {
            setListPortal(job.getTargets());
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
    public interface JobReviewFragmentListener
    {
        // TODO: Update argument type and name
        public void onViewListButtonPressed();

        public void onViewMapButtonPressed();
    }

}
