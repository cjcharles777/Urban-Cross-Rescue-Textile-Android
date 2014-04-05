package com.ucr.bravo.blackops.rest.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.ucr.bravo.blackops.R;

/**
 * Created by cedric on 4/4/14.
 */
public abstract class NetworkCommunicationUtil
{
    public void processNetworkTask(Activity activity)
    {
        ConnectivityManager connMgr = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            runTask();
        }
        else
        {
            onError(activity);
        }
    }

    protected void onError(Activity activity)
    {
        Toast.makeText(activity, activity.getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
    }

    protected abstract void runTask();
}
