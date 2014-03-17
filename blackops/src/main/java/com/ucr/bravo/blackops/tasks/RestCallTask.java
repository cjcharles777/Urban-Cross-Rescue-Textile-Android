package com.ucr.bravo.blackops.tasks;

import android.os.AsyncTask;

import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.RestCallAction;
import com.ucr.bravo.blackops.rest.object.RestCallObject;

import java.io.IOException;

/**
 * Created by cedric on 2/7/14.
 */
public abstract class RestCallTask extends AsyncTask<String, Void, String>
{
    private final BaseRestPostAction baseRestPostAction;


    protected RestCallTask(BaseRestPostAction baseRestPostAction)
    {
        this.baseRestPostAction = baseRestPostAction;
    }

    protected String callRest(String myurl, String getPost) throws IOException
    {
        return callRest(myurl, getPost, null);
    }


    protected String callRest(String myurl, String getPost, String jsonParamsString) throws IOException
    {
        return RestCallAction.executeRestCall(new RestCallObject(myurl,getPost,jsonParamsString));
    }

    @Override
    protected void onPostExecute(String result)
    {
        baseRestPostAction.onPostExecution(result);
    }
}

