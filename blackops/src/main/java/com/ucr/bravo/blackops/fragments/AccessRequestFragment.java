package com.ucr.bravo.blackops.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.activities.MainActivity;

/**
 * Created by cedric on 2/7/14.
 */
public class AccessRequestFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_access_request, container, false);
        Intent intent = getActivity().getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView emailTxt = (TextView)rootView.findViewById(R.id.emailText);
        emailTxt.setText(message);
        return rootView;
    }

}
