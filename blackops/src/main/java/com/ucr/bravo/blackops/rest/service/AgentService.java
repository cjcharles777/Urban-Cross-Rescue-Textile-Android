package com.ucr.bravo.blackops.rest.service;

import com.google.gson.Gson;
import com.ucr.bravo.blackops.rest.BaseRest;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.object.Agent;

/**
 * Created by cedric on 2/10/14.
 */
public class AgentService extends BaseRest
{
    protected static String REQUEST_SECTION =  "/agent";

    public AgentService()
    {
        super(REQUEST_SECTION);
    }

    public void requestAuthorization(BaseRestPostAction baseRestPostAction, Agent agent)
    {
        Gson gson = new Gson();
        String json = gson.toJson(agent);
        String endPoint = "/requestAuthorization";
        executePostCall("/requestAuthorization", baseRestPostAction, json);
    }

}
