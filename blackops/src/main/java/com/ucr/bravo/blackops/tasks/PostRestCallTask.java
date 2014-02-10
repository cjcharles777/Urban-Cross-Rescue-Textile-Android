package com.ucr.bravo.blackops.tasks;

import java.io.IOException;

/**
 * Created by cedric on 2/10/14.
 */
public class PostRestCallTask extends RestCallTask
{
    @Override
    protected String doInBackground(String... urls) {
        // params comes from the execute() call: params[0] is the url.
        try {
            return callRest(urls[0], "POST");
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }    }
}
