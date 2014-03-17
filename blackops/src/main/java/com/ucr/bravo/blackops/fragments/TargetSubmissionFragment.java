package com.ucr.bravo.blackops.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.ucr.bravo.blackops.R;

/**
 * Created by cedric on 3/14/14.
 */
public class TargetSubmissionFragment extends Fragment
{
    private AutoCompleteTextView actv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_target_submission, container, false);

        actv = (AutoCompleteTextView) rootView.findViewById(R.id.portalAutoCompleteTextView);

        return rootView;
    }


}
