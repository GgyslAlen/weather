package com.weathertech.weather.api.exceptions;

import com.weathertech.weather.api.dto.RestResponse;

public class RestException extends Exception {

    public static int DEFAULT_ERR_CODE = 500;

    private RestResponse restError;

    public RestException(RestResponse re)
    {
        super(re.getErrMessage());
        restError = re;
    }

    public RestException(String error)
    {
        super(error);
        restError = new RestResponse(DEFAULT_ERR_CODE, error);
    }

    public RestResponse getRestError() {
        return restError;
    }

    public void setRestError(RestResponse restError) {
        this.restError = restError;
    }


    @Override
    public String getMessage()
    {
        return "" + restError;
    }

}
