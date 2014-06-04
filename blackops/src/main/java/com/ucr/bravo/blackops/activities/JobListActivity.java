package com.ucr.bravo.blackops.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ucr.bravo.blackops.BlackOpsApplication;
import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.fragments.JobListFragment;
import com.ucr.bravo.blackops.fragments.JobReviewFragment;
import com.ucr.bravo.blackops.fragments.PortalListMapFragment;
import com.ucr.bravo.blackops.fragments.PortalListReviewFragment;
import com.ucr.bravo.blackops.rest.object.beans.Agent;
import com.ucr.bravo.blackops.rest.object.beans.Job;
import com.ucr.bravo.blackops.rest.object.beans.Portal;

import java.util.ArrayList;

public class JobListActivity extends LocationActivity
implements JobListFragment.JobListFragmentListener, JobReviewFragment.JobReviewFragmentListener,
        PortalListReviewFragment.PortalListReviewFragmentListener
{
    private JobListFragment firstFragment ;
    private static final int MENU_SETTINGS = Menu.FIRST;
    private static final int MENU_SUBMIT = Menu.FIRST + 1;
    private static final int MENU_ADMIN = Menu.FIRST + 2;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        FragmentManager fm = getSupportFragmentManager();
        firstFragment = (JobListFragment) fm.findFragmentByTag("jobListFragment");
        if (findViewById(R.id.container) != null)
        {
            if (savedInstanceState != null)
            {

            }
            else
            {

                if (firstFragment == null)
                {
                    firstFragment = new JobListFragment();
                    firstFragment.setArguments(getIntent().getExtras());
                    fm.beginTransaction().add(R.id.container, firstFragment, "jobListFragment").commit();
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.target_list, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    //  menu.clear();
    //    menu.add(0, MENU_SETTINGS, Menu.NONE, R.string.action_settings);
    //    menu.add(0, MENU_SUBMIT, Menu.NONE, R.string.submit_target);

        Agent agent = ((BlackOpsApplication)getApplication()).getSessionAgent();
        if(agent.getClearance() > 1 && menu.findItem(MENU_ADMIN) == null)
        {
            menu.add(0, MENU_ADMIN, Menu.NONE, R.string.administrator);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId())
        {
            case  R.id.action_settings:
                 return true;
            case  R.id.action_submit:
                openPortalSubmit();
                return true;
            case MENU_ADMIN:
                openAdministratorOptions();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void openAdministratorOptions()
    {
        Intent intent = new Intent(this, PendingUserAcceptActivity.class);
        startActivity(intent);
    }

    private void openPortalSubmit()
    {
        Intent intent = new Intent(this, JobSubmissionActivity.class);
        startActivity(intent);
    }


    @Override
    public void onListItemClick(Job job)
    {
        JobReviewFragment jobReviewFragment = new JobReviewFragment();
        jobReviewFragment.setJob(job);
        jobReviewFragment.setRetainInstance(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, jobReviewFragment, "selectedJobFragment");
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }


    @Override
    public ArrayList<Portal> retrieveCurrentPortalList()
    {
        FragmentManager fm = getSupportFragmentManager();
        JobReviewFragment jobReviewFragment = (JobReviewFragment) fm.findFragmentByTag("selectedJobFragment");
        if(jobReviewFragment != null && jobReviewFragment.getJob() != null)
        {
            return jobReviewFragment.getJob().getTargets();
        }
        return new ArrayList<Portal>();
    }

    @Override
    public void onViewListButtonPressed()
    {
        //PortalListSelectionFragment plistFragment = (PortalListSelectionFragment)
        //      getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        PortalListReviewFragment plistFragment = new PortalListReviewFragment();
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
    public void onViewMapButtonPressed()
    {
        //PortalListSelectionFragment plistFragment = (PortalListSelectionFragment)
        //      getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        PortalListMapFragment map = new PortalListMapFragment();
        Bundle args = new Bundle();
        // args.putParcelableArrayList(PortalListSelectionFragment.ARG_PORTAL_LIST, (ArrayList<Portal>)pList);
        //plistFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, map);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();


    }
}
