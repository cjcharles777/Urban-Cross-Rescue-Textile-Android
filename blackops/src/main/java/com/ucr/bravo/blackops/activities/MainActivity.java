package com.ucr.bravo.blackops.activities;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.internal.el;
import com.ucr.bravo.blackops.BlackOpsApplication;
import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.fragments.AgentLocationFragment;
import com.ucr.bravo.blackops.fragments.BasePortalListFragment;
import com.ucr.bravo.blackops.fragments.ComingSoonFragment;
import com.ucr.bravo.blackops.fragments.GPlusLoginFragment;
import com.ucr.bravo.blackops.fragments.JobListFragment;
import com.ucr.bravo.blackops.fragments.JobReviewFragment;
import com.ucr.bravo.blackops.fragments.JobSubmissionFragment;
import com.ucr.bravo.blackops.fragments.PortalListMapFragment;
import com.ucr.bravo.blackops.fragments.PortalListReviewFragment;
import com.ucr.bravo.blackops.fragments.PortalListSelectionFragment;
import com.ucr.bravo.blackops.listeners.AppLocationListener;
import com.ucr.bravo.blackops.listeners.PortalListListener;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.object.beans.AgentLocation;
import com.ucr.bravo.blackops.rest.object.beans.Job;
import com.ucr.bravo.blackops.rest.object.beans.Portal;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;
import com.ucr.bravo.blackops.rest.service.AgentService;
import com.ucr.bravo.blackops.rest.utils.JsonResponseConversionUtil;
import com.ucr.bravo.blackops.rest.utils.NetworkCommunicationUtil;
import com.ucr.bravo.blackops.utils.LocationUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends LocationActivity
        implements JobListFragment.JobListFragmentListener, JobReviewFragment.JobReviewFragmentListener,
        PortalListListener, AppLocationListener, JobSubmissionFragment.OnAddPortalsListener
{

    public final static String EXTRA_MESSAGE = "com.ucr.bravo.blackops.MESSAGE";
    public final static String EXTRA_GID = "com.ucr.bravo.blackops.GID";
    public final static String AUTHORIZED_AGENT = "com.ucr.bravo.blackops.AUTHORIZEDAGENT";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mNavigationTitles;
    private AgentService agentService = new AgentService();
    private BasePortalListFragment firstFragment ;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mTitle = mDrawerTitle = getTitle();
    mNavigationTitles = getResources().getStringArray(R.array.nav_array);
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerList = (ListView) findViewById(R.id.left_drawer);

    // set a custom shadow that overlays the main content when the drawer opens
    mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    // set up the drawer's list view with items and click listener
    mDrawerList.setAdapter(new ArrayAdapter<String>(this,
            R.layout.drawer_list_item, mNavigationTitles));
    mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    // enable ActionBar app icon to behave as action to toggle nav drawer
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);

    // ActionBarDrawerToggle ties together the the proper interactions
    // between the sliding drawer and the action bar app icon
    mDrawerToggle = new ActionBarDrawerToggle(
            this,                  /* host Activity */
            mDrawerLayout,         /* DrawerLayout object */
            R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
            R.string.drawer_open,  /* "open drawer" description for accessibility */
            R.string.drawer_close  /* "close drawer" description for accessibility */
    ) {
        public void onDrawerClosed(View view) {
            getActionBar().setTitle(mTitle);
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }

        public void onDrawerOpened(View drawerView) {
            getActionBar().setTitle(mDrawerTitle);
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }
    };
    mDrawerLayout.setDrawerListener(mDrawerToggle);

    if (savedInstanceState == null) {
        selectItem(-1);
    }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

       // menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Location getCurrentLocation() {
        return getLocation();
    }

    @Override
    public void onLocationChanged(Location location)
    {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Log.e(LocationUtils.APPTAG, msg);

        final AgentLocation agentLocation = new AgentLocation();
        agentLocation.setLatitude(new BigDecimal(location.getLatitude()));
        agentLocation.setLongitude(new BigDecimal(location.getLongitude()));
        final BaseRestPostAction baseRestPostAction = new BaseRestPostAction(this)
        {


            @Override
            public void onSuccess(BaseResponse response) {

            }
        };
        NetworkCommunicationUtil network = new NetworkCommunicationUtil()
        {
            @Override
            protected void runTask()
            {
                agentService.updateAgentLocation(baseRestPostAction, agentLocation, ((BlackOpsApplication) getApplication()).getRequesterId());
            }
        };
        network.processNetworkTask(this);

    }



    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position)
        {
            case -1: fragment = new GPlusLoginFragment();
                     setTitle("Login");
                     break;
            case 0: fragment = new ComingSoonFragment();
                    setTitle("MVP's");
                    break;
            case 1: fragment = new ComingSoonFragment();
                    setTitle("Farms");
                    break;
            case 2: fragment = new JobListFragment();
                    setTitle("Jobs");
                    break;
            case 3: fragment = new AgentLocationFragment();
                    setTitle("Agent Location");
                    break;
            default: fragment = new JobListFragment();
                     setTitle("Jobs");
                     break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        switchFragments(fragment, false, false);

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);

        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public void switchFragments(Fragment fragment, boolean isAddedToBackStack, boolean isPortalListFragment)
    {
        fragment.setArguments(getIntent().getExtras());


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(isPortalListFragment)
        {
            transaction.replace(R.id.content_frame, fragment, "selectedJobFragment");
            firstFragment = (BasePortalListFragment)fragment;
        }
        else
        {
            transaction.replace(R.id.content_frame, fragment);
        }
        if(isAddedToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
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
        transaction.replace(R.id.content_frame, jobReviewFragment, "selectedJobFragment");
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
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
        transaction.replace(R.id.content_frame, plistFragment);
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
        transaction.replace(R.id.content_frame, map);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onAddButtonPressed(List<Portal> pList)
    {
        //PortalListSelectionFragment plistFragment = (PortalListSelectionFragment)
        //      getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        PortalListSelectionFragment plistFragment = new PortalListSelectionFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.content_frame, plistFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }


    @Override
    public void onSubmitPortalsListButtonPressed(List<Portal> pList)
    {
        BasePortalListFragment jobSubmissionFragment = firstFragment;
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, jobSubmissionFragment).commit();
    }



    @Override
    public ArrayList<Portal> retrieveCurrentPortalList()
    {
        FragmentManager fm = getSupportFragmentManager();
        BasePortalListFragment jobReviewFragment = (BasePortalListFragment) fm.findFragmentByTag("selectedJobFragment");
        if(jobReviewFragment != null) {
            return jobReviewFragment.getListPortal();
        }
        else
        {
            return new ArrayList<Portal>();
        }

    }
}




