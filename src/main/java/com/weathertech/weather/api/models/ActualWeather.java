package com.weathertech.weather.api.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("actual_weather")
public class ActualWeather {

    @Id
    @Column("id")
    private Long id;

    @Column("city_id")
    private Long cityId;

    @Column("degrees_celsius")
    private BigDecimal degreesCelsius;

    @Column("wind_speed_mps")
    private Integer windSpeedMPS;

    public ActualWeather(Long id, Long cityId, BigDecimal degreesCelsius, Integer windSpeedMPS) {
        this.id = id;
        this.cityId = cityId;
        this.degreesCelsius = degreesCelsius;
        this.windSpeedMPS = windSpeedMPS;
    }

    public ActualWeather() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
