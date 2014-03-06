package com.ucr.bravo.blackops.rest;

import com.ucr.bravo.blackops.tasks.GetRestCallTask;
import com.ucr.bravo.blackops.tasks.PostRestCallTask;

/**
 * Created by cedric on 2/10/14.
 */
public class BaseRestService
{

    protected final static String BASE_URL = "http://donkeigy.endofinternet.net:18077/black-ops/rest";
    private String requestSection;

    public BaseRestService(String requestSection)
    {
        this.requestSection = requestSection;
    }

    public String getRequestSection() {
        return requestSection;
    }

    public void setRequestSection(String requestSection) {
        this.requestSection = requestSection;
    }

    public void executePostCall(String endpoint, BaseRestPostAction baseRestPostAction, String jsonParamsString)
    {
        new PostRestCallTask(baseRestPostAction, jsonParamsString ).execute(BASE_URL + requestSection + endpoint);
    }
    public void executeGetCall(String endpoint, BaseRestPostAction baseRestPostAction)
    {
        new GetRestCallTask(baseRestPostAction).execute(BASE_URL + requestSection + endpoint);
    }

}
