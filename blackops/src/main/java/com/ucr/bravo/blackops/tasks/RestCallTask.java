package com.ucr.bravo.blackops.tasks;

import android.os.AsyncTask;

import com.ucr.bravo.blackops.rest.BaseRestPostAction;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

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


    protected String callRest(String myurl, String getPost, String jsonParamsString) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
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
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
    @Override
    protected void onPostExecute(String result)
    {
        baseRestPostAction.onPostExecution(result);
    }
}

