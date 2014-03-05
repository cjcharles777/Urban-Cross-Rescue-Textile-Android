package com.ucr.bravo.blackops.rest.object.response;

/**
 * Created by cedric on 3/4/14.
 */
public class BaseResponse<T>
{
    private String result;
    private T message;

    public BaseResponse(String result) {
        this.result = result;
    }

    public BaseResponse(String result, T message) {
        this.result = result;
        this.message = message;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
