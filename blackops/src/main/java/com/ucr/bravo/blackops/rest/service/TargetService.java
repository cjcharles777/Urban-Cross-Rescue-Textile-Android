package com.ucr.bravo.blackops.rest.service;

import com.google.gson.Gson;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.BaseRestService;
import com.ucr.bravo.blackops.rest.object.beans.Target;
import com.ucr.bravo.blackops.rest.object.request.BaseRequest;

/**
 * Created by cedric on 3/5/14.
 */
public class TargetService extends BaseRestService
{

    protected static String REQUEST_SECTION =  "/target";

    public TargetService()
    {
        super(REQUEST_SECTION);
    }

    public void retrieveAllTargets(BaseRestPostAction baseRestPostAction, Target target, String requesterId)
    {
        Gson gson = new Gson();
        String json = gson.toJson(new BaseRequest<Target>(requesterId, target));
        String endPoint = "/retrieve";
        executePostCall(endPoint, baseRestPostAction, json);
    }
}
