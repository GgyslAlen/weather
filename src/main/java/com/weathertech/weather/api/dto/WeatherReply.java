package com.weathertech.weather.api.dto;

import javax.persistence.Column;
import java.math.BigDecimal;

public class WeatherReply {

    private String cityName;

    private BigDecimal degreesCelsius;

    private Integer windSpeedMPS;

    public WeatherReply() {

    }

    public WeatherReply(String cityName, BigDecimal degreesCelsius, Integer windSpeedMPS) {
        this.cityName = cityName;
        this.degreesCelsius = degreesCelsius;
        this.windSpeedMPS = windSpeedMPS;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public BigDecimal getDegreesCelsius() {
        return degreesCelsius;
    }

    public void setDegreesCelsius(BigDecimal degreesCelsius) {
        this.degreesCelsius = degreesCelsius;
    }

    public Integer getWindSpeedMPS() {
        return windSpeedMPS;
    }

    public void setWindSpeedMPS(Integer windSpeedMPS) {
        this.windSpeedMPS = windSpeedMPS;
    }
}
