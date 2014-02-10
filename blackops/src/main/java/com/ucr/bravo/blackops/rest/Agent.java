package com.ucr.bravo.blackops.rest;

/**
 * Created by cedric on 2/10/14.
 */
public class Agent extends BaseRest
{
    protected static String REQUEST_SECTION =  "/agent";

    public static void requestAuthorization()
    {
        String endPoint = "/requestAuthorization";
        executePostCall("/requestAuthorization");
    }_

}
