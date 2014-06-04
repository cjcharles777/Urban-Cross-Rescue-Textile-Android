package com.ucr.bravo.blackops.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.fragments.GPlusLoginFragment;

public class MainActivity extends ActionBarActivity  {

    public final static String EXTRA_MESSAGE = "com.ucr.bravo.blackops.MESSAGE";
    public final static String EXTRA_GID = "com.ucr.bravo.blackops.GID";
    public final static String AUTHORIZED_AGENT = "com.ucr.bravo.blackops.AUTHORIZEDAGENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new GPlusLoginFragment())
                    .commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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



}
