package com.ucr.bravo.blackops.rest;

import com.ucr.bravo.blackops.tasks.GetRestCallTask;
import com.ucr.bravo.blackops.tasks.PostRestCallTask;

/**
 * Created by cedric on 2/10/14.
 */
public class BaseRest
{

    protected final static String BASE_URL = "http://donkeigy.endofinternet.net/BlackOpsRestWeb/rest";
    protected static String REQUEST_SECTION;


    protected static void executePostCall(String endpoint)
    {
        new PostRestCallTask().execute(BASE_URL + REQUEST_SECTION + endpoint);
    }
    protected static void executeGetCall(String endpoint)
    {
        new GetRestCallTask().execute(BASE_URL + REQUEST_SECTION + endpoint);
    }

}
