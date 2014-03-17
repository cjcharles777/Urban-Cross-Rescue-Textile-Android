package com.ucr.bravo.blackops.rest;

import com.ucr.bravo.blackops.rest.object.RestCallObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cedric on 3/14/14.
 */
public class RestCallAction
{
    public static String executeRestCall(RestCallObject restCall) throws IOException
    {
        return executeRestCall(restCall.getExtension() , restCall.getGetPost() , restCall.getJsonParamsString());
    }
    public static String executeRestCall(String myUrl, String getPost, String jsonParamsString) throws IOException
    {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try
        {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type","application/json");
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod(getPost);
            conn.setDoInput(true);

            if(getPost.equals("POST") && jsonParamsString != null)
            {
                conn.setDoOutput(true);
                DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
                outputStream.writeBytes(jsonParamsString);
                outputStream.flush();
                outputStream.close();
            }
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //  Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }
    private static String readIt(InputStream stream) throws IOException
    {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        br = new BufferedReader(new InputStreamReader(stream));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
