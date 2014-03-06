package com.ucr.bravo.blackops.rest.object.request;

import com.ucr.bravo.blackops.rest.object.Target;

/**
 * Created by cedric on 3/4/14.
 */
public class BaseRequest<T>
{
    private String requesterId;
    private T request;


    public BaseRequest(T request) {
        this.request = request;
    }


    public BaseRequest(String requesterId, T request) {
        this.requesterId = requesterId;
        this.request = request;
    }


    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }


}
