package com.weathertech.weather.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonElement;

public class RestResponse {

    private int error = 0;
    private String errMessage = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errIconFileName = null;
    private Object data = null;
    @JsonIgnore
    private String provider = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String referenceId = null;

    public static RestResponse ERROR_SERVICE_UNAVAILABLE = new RestResponse(500, "Service unavailable");
    public static RestResponse ERROR_WRONG_PARAMETERS = new RestResponse(400, "Wrong parameters");
    public static RestResponse ERROR_NOT_FOUND = new RestResponse(404, "Not found");
    public static RestResponse ERROR_FORBIDDEN = new RestResponse(403, "Not allowed");
    public static final RestResponse OK = new RestResponse(0, "ok");

    public RestResponse(int errorCode, String errorMessage, String provider, String iconFileName, JsonElement parse) {

    }

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

    public RestResponse(Object o, int error, String errMessage, String provider)
    {
        this.data = o;
        this.error = error;
        this.errMessage = errMessage;
        this.provider = provider;
    }

    public RestResponse(int error, String errMessage, String provider)
    {
        this.error = error;
        this.errMessage = errMessage;
        this.provider = provider;
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

    @Override
    public String toString() {
        return "RestError{" + "error=" + error + ", errMessage=" + errMessage + ", data=" + data + '}';
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getErrIconFileName() {
        return errIconFileName;
    }

    public void setErrIconFileName(String errIconFileName) {
        this.errIconFileName = errIconFileName;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

}
