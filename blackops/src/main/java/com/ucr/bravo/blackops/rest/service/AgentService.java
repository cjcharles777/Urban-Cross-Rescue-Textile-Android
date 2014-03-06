package com.ucr.bravo.blackops.rest.service;

import com.google.gson.Gson;
import com.ucr.bravo.blackops.rest.BaseRestService;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.object.Agent;
import com.ucr.bravo.blackops.rest.object.request.BaseRequest;

/**
 * Created by cedric on 2/10/14.
 */
public class AgentService extends BaseRestService
{
    protected static String REQUEST_SECTION =  "/agent";

    public AgentService()
    {
        super(REQUEST_SECTION);
    }

    public void requestAuthorization(BaseRestPostAction baseRestPostAction, Agent agent)
    {
        Gson gson = new Gson();
        String json = gson.toJson(new BaseRequest<Agent>(agent));
        String endPoint = "/requestAuthorization";
        executePostCall(endPoint, baseRestPostAction, json);
    }

    public void retrieveAgentByEmail(BaseRestPostAction baseRestPostAction, Agent agent)
    {
        Gson gson = new Gson();
        String json = gson.toJson(new BaseRequest<Agent>(agent));
        String endPoint = "/retrieve/email";
        executePostCall(endPoint, baseRestPostAction, json);
    }

}
