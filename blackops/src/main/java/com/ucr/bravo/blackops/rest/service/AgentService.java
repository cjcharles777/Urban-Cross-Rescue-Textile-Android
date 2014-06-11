package com.ucr.bravo.blackops.rest.service;

import com.google.gson.Gson;
import com.ucr.bravo.blackops.rest.BaseRestService;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.object.beans.Agent;
import com.ucr.bravo.blackops.rest.object.beans.AgentLocation;
import com.ucr.bravo.blackops.rest.object.beans.LocationBounds;
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
    public void retrieveAgentByGID(BaseRestPostAction baseRestPostAction, Agent agent)
    {
        Gson gson = new Gson();
        String json = gson.toJson(new BaseRequest<Agent>(agent));
        String endPoint = "/retrieve/gid";
        executePostCall(endPoint, baseRestPostAction, json);
    }
    public void retrieveAgentByExample(BaseRestPostAction baseRestPostAction, Agent agent, String requesterId)
    {
        Gson gson = new Gson();
        String json = gson.toJson(new BaseRequest<Agent>(requesterId, agent));
        String endPoint = "/retrieve/example";
        executePostCall(endPoint, baseRestPostAction, json);
    }
    public void updateAgentLocation(BaseRestPostAction baseRestPostAction, AgentLocation agentLocation, String requesterId)
    {
        Gson gson = new Gson();
        String json = gson.toJson(new BaseRequest<AgentLocation>(requesterId, agentLocation));
        String endPoint = "/location/update";
        executePostCall(endPoint, baseRestPostAction, json);
    }
    public void retrieveAgentLocation(BaseRestPostAction baseRestPostAction, LocationBounds locationBounds, String requesterId)
    {
        Gson gson = new Gson();
        String json = gson.toJson(new BaseRequest<LocationBounds>(requesterId, locationBounds));
        String endPoint = "/location/retrieve";
        executePostCall(endPoint, baseRestPostAction, json);
    }

}
