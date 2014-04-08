package com.ucr.bravo.blackops.activities;


import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.fragments.JobSubmissionFragment;
import com.ucr.bravo.blackops.fragments.PortalListSelectionFragment;
import com.ucr.bravo.blackops.rest.object.beans.Portal;

import java.util.ArrayList;
import java.util.List;

public class JobSubmissionActivity extends LocationActivity
        implements JobSubmissionFragment.OnAddPortalsListener, PortalListSelectionFragment.PortalListSelectionFragmentListener
{

    private static final String ARG_PORTAL_LIST = "portalList";
    JobSubmissionFragment firstFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_submission);
        FragmentManager fm = getSupportFragmentManager();
        firstFragment = (JobSubmissionFragment) fm.findFragmentByTag("submissionFragment");
        if (findViewById(R.id.container) != null) {
            if (savedInstanceState != null) {

            }
            else
            {

                if (firstFragment == null) {
                    firstFragment = new JobSubmissionFragment();
                    firstFragment.setArguments(getIntent().getExtras());
                    fm.beginTransaction().add(R.id.container, firstFragment, "submissionFragment").commit();
                }
            }
        }
    }

    @Override
    protected void onReceivedAddress(String address)
    {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.target_submission, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAddButtonPressed(List<Portal> pList)
    {
        //PortalListSelectionFragment plistFragment = (PortalListSelectionFragment)
          //      getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        PortalListSelectionFragment plistFragment = new PortalListSelectionFragment();
        Bundle args = new Bundle();
       // args.putParcelableArrayList(PortalListSelectionFragment.ARG_PORTAL_LIST, (ArrayList<Portal>)pList);
        //plistFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, plistFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onSubmitPortalsListButtonPressed(List<Portal> pList)
    {
        JobSubmissionFragment jobSubmissionFragment = firstFragment;
        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        //Bundle args = new Bundle();
        //args.putParcelableArrayList(PortalListSelectionFragment.ARG_PORTAL_LIST, (ArrayList<Portal>)pList);
        //jobSubmissionFragment.setUIArguments(args);
        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, jobSubmissionFragment).commit();

    }

    @Override
    public ArrayList<Portal> retrieveCurrentPortalList()
    {
        return firstFragment.getListPortal();
    }


    @Override
    public void onLocationChanged(Location location) {

    }
}
