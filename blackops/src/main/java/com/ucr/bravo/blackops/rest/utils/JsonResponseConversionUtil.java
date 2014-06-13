package com.ucr.bravo.blackops.rest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ucr.bravo.blackops.rest.object.response.BaseResponse;
import com.ucr.bravo.blackops.utils.CustomDateDeserializer;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Created by cedric on 3/14/14.
 */
public class JsonResponseConversionUtil
{
    public static BaseResponse convertToResponse(String str)
    {
        Gson gson = getCustomGson();
        JsonReader reader = new JsonReader(new StringReader(str));
        reader.setLenient(true);
        BaseResponse response = gson.fromJson(reader, BaseResponse.class);
        return response;
    }
    public static Object convertMessageToObject(Object o, Class c)
    {
        Gson gson = getCustomGson();
        String json = gson.toJson(o);
        Object response = gson.fromJson(json, c);
        return response;
    }
    public static <T> List<T> convertMessageToObjectList(Object o, TypeToken<List<T>> t)
    {
        Gson gson = getCustomGson();
        String json = gson.toJson(o);

        List response = gson.fromJson(json,t.getType());
        return response;
    }
    public static Object convertMessageToObject(Object o, Type c)
    {
        Gson gson = getCustomGson();
        String json = gson.toJson(o);
        Object response = gson.fromJson(json, c);
        return response;
    }

    private static Gson getCustomGson()
    {
        return new GsonBuilder().registerTypeAdapter(Date.class, new CustomDateDeserializer()).create();
    }
}
