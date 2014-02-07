package com.ucr.bravo.blackops.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusClient;
import com.ucr.bravo.blackops.AccessRequestActivity;
import com.ucr.bravo.blackops.R;

/**
 * Created by cedric on 2/6/14.
 */
public class GPlusLoginFragment extends Fragment implements
        GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener
{

    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
    private ProgressDialog mConnectionProgressDialog;
    private PlusClient mPlusClient;
    private ConnectionResult mConnectionResult;
    private View rootView;
    public final static String EXTRA_MESSAGE = "com.ucr.bravo.blackops.MESSAGE";

    public GPlusLoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mPlusClient = new PlusClient.Builder(getActivity(), this, this)
                .setActions("http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity")
                .setScopes(Scopes.PLUS_LOGIN)  // recommended login scope for social features
                        // .setScopes("profile")       // alternative basic login scope
                .build();
        // Progress bar to be displayed if the connection failure is not resolved.
        mConnectionProgressDialog = new ProgressDialog(getActivity());
        mConnectionProgressDialog.setMessage("Signing in...");
        // note that we're looking for a button with id="@+id/sign_in_button" in your inflated layout
        // Naturally, this can be any View; it doesn't have to be a button
        SignInButton mButton = (SignInButton) rootView.findViewById(R.id.sign_in_button);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                if (view.getId() == R.id.sign_in_button && !mPlusClient.isConnected()) {
                    if (mConnectionResult == null) {
                        mConnectionProgressDialog.show();
                    } else {
                        try {
                            mConnectionResult.startResolutionForResult(getActivity(), REQUEST_CODE_RESOLVE_ERR);
                        } catch (IntentSender.SendIntentException e) {
                            // Try connecting again.
                            mConnectionResult = null;
                            mPlusClient.connect();
                        }
                    }
                }

            }

        });
        return rootView;
    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (mConnectionProgressDialog.isShowing()) {
            // The user clicked the sign-in button already. Start to resolve
            // connection errors. Wait until onConnected() to dismiss the
            // connection dialog.
            if (result.hasResolution()) {
                try {
                    result.startResolutionForResult(getActivity(), REQUEST_CODE_RESOLVE_ERR);
                } catch (IntentSender.SendIntentException e) {
                    mPlusClient.connect();
                }
            }
        }
        // Save the result and resolve the connection failure upon a user click.
        mConnectionResult = result;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPlusClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPlusClient.disconnect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mConnectionProgressDialog.dismiss();

        Toast.makeText(getActivity(), mPlusClient.getAccountName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisconnected() {
        //  Log.d(TAG, "disconnected");
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, AccessRequestActivity.class);
        EditText editText = (EditText) rootView.findViewById(R.id.emailEditText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

    }

}


