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

    public BaseResponse convertToResponse(String str)
    {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(str));
        reader.setLenient(true);
        BaseResponse response = gson.fromJson(reader, BaseResponse.class);
        return response;
    }
    public Object convertMessageToObject(Object o, Class c)
    {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        Object response = gson.fromJson(json, c);
        return response;
    }
}
