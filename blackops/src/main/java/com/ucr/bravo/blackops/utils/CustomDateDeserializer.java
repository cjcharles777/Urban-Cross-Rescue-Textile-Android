package com.ucr.bravo.blackops.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by cedric on 6/12/14.
 */
public class CustomDateDeserializer implements JsonDeserializer<Date>
{


    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String s = json.getAsJsonPrimitive().getAsString();
        long l = new Double(Double.parseDouble(s)).longValue();
        Date d = new Date(l);
        return d;
    }
}