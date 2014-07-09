package com.ucr.bravo.blackops.rest;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ucr.bravo.blackops.R;
import com.ucr.bravo.blackops.activities.MainActivity;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;
import com.ucr.bravo.blackops.rest.utils.JsonResponseConversionUtil;

import java.io.StringReader;

/**
 * Created by cedric on 2/12/14.
 */
public abstract class BaseRestPostAction
{
    private Activity fragment;

    protected BaseRestPostAction(Activity fragment) {
        this.fragment = fragment;
    }

    public void onPostExecution(String str) {
        BaseResponse response = JsonResponseConversionUtil.convertToResponse(str);
        if(response.getResult().equals("SUCCESS"))
        {
            onSuccess(response);
        }
        else
        {
           onFailure(response);
        }

    }

    public abstract void onSuccess(BaseResponse response);

    public void onFailure(BaseResponse response)
    {
        String message = (String) response.getMessage();
        if(message != null && !message.trim().isEmpty())
        {
            Toast.makeText(fragment,message,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(fragment, fragment.getString(R.string.request_error), Toast.LENGTH_LONG).show();
        }

    }
}
