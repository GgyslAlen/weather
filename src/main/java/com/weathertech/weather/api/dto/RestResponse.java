package com.weathertech.weather.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonElement;

public class RestResponse {

    private int error = 0;
    private String errMessage = null;
    private Object data = null;

    public static final RestResponse ERROR_SERVICE_UNAVAILABLE = new RestResponse(500, "Service unavailable");
    public static final RestResponse ERROR_WRONG_PARAMETERS = new RestResponse(400, "Wrong parameters");
    public static final RestResponse ERROR_NOT_FOUND = new RestResponse(404, "Not found");
    public static final RestResponse ERROR_FORBIDDEN = new RestResponse(403, "Not allowed");
    public static final RestResponse OK = new RestResponse(0, "ok");

    public RestResponse(int error, String errMessage)
    {
        this.error = error;
        this.errMessage = errMessage;
    }

    public RestResponse(Object o)
    {
        this.data = o;
    }

    public RestResponse(Object o, String errMessage)
    {
        this.data = o;
        this.errMessage = errMessage;
    }

    public RestResponse(Object o, int error, String errMessage)
    {
        this.data = o;
        this.error = error;
        this.errMessage = errMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }


}
