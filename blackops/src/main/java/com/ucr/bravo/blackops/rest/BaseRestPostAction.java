package com.ucr.bravo.blackops.rest;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;

import java.io.StringReader;

/**
 * Created by cedric on 2/12/14.
 */
public abstract class BaseRestPostAction
{
    public abstract void onPostExecution(String str);


}
