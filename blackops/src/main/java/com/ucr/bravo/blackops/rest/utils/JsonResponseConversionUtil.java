package com.ucr.bravo.blackops.rest.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;

import java.io.StringReader;
import java.lang.reflect.Type;

/**
 * Created by cedric on 3/14/14.
 */
public class JsonResponseConversionUtil
{
    public static BaseResponse convertToResponse(String str)
    {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(str));
        reader.setLenient(true);
        BaseResponse response = gson.fromJson(reader, BaseResponse.class);
        return response;
    }
    public static Object convertMessageToObject(Object o, Class c)
    {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        Object response = gson.fromJson(json, c);
        return response;
    }
    public static Object convertMessageToObject(Object o, Type c)
    {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        Object response = gson.fromJson(json, c);
        return response;
    }
}
