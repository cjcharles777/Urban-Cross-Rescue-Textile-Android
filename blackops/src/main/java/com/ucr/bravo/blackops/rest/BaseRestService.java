package com.ucr.bravo.blackops.rest;

import com.ucr.bravo.blackops.tasks.GetRestCallTask;
import com.ucr.bravo.blackops.tasks.PostRestCallTask;

/**
 * Created by cedric on 2/10/14.
 */
public class BaseRestService
{


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
        new PostRestCallTask(baseRestPostAction, jsonParamsString ).execute(requestSection + endpoint);
    }
    public void executeGetCall(String endpoint, BaseRestPostAction baseRestPostAction)
    {
        new GetRestCallTask(baseRestPostAction).execute(requestSection + endpoint);
    }

}
