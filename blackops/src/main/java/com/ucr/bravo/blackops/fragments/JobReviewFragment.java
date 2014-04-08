package com.ucr.bravo.blackops.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.rest.object.beans.Agent;
import com.ucr.bravo.blackops.rest.object.beans.Job;
import com.ucr.bravo.blackops.rest.object.beans.Portal;

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
public class JobReviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Job job;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private JobReviewFragmentListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobReviewFragment newInstance(String param1, String param2) {
        JobReviewFragment fragment = new JobReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public JobReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_job_review, container, false);
        TextView jobHeadlineTextView = (TextView) rootView.findViewById(R.id.jobHeadlineTextView);
        TextView numOfPortalstextView = (TextView) rootView.findViewById(R.id.numOfPortalsTextView);
        TextView jobDetailsTextView  = (TextView) rootView.findViewById(R.id.jobDetailsTextView);
        TextView requesterLabelTextView  = (TextView) rootView.findViewById(R.id.requesterLabelTextView);
        Button addButton = (Button) rootView.findViewById(R.id.viewPortalsButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onAddButtonPressed();
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
            requesterLabelTextView.setText(requester.getIgn());
        }
        return rootView;

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
        public void onAddButtonPressed();
    }

}
