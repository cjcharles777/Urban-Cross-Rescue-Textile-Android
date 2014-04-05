package com.ucr.bravo.blackops.rest.service;

import com.google.gson.Gson;
import com.ucr.bravo.blackops.rest.BaseRestPostAction;
import com.ucr.bravo.blackops.rest.BaseRestService;
import com.ucr.bravo.blackops.rest.object.beans.Job;
import com.ucr.bravo.blackops.rest.object.request.BaseRequest;

/**
 * Created by cedric on 3/5/14.
 */
public class JobService extends BaseRestService
{

    protected static String REQUEST_SECTION =  "/job";

    public JobService()
    {
        super(REQUEST_SECTION);
    }

    public void retrieveJobs(BaseRestPostAction baseRestPostAction, Job job, String requesterId)
    {
        Gson gson = new Gson();
        String json = gson.toJson(new BaseRequest<Job>(requesterId, job));
        String endPoint = "/retrieve";
        executePostCall(endPoint, baseRestPostAction, json);
    }
    public void submit(BaseRestPostAction baseRestPostAction, Job job, String requesterId)
    {
        Gson gson = new Gson();
        String json = gson.toJson(new BaseRequest<Job>(requesterId, job));
        String endPoint = "/submit";
        executePostCall(endPoint, baseRestPostAction, json);
    }
}
