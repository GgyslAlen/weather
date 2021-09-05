package com.weathertech.weather.api.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("cities")
public class City {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("city_desc")
    private String cityDesc;

    public City(Long id, String name, String cityDesc) {
        this.id = id;
        this.name = name;
        this.cityDesc = cityDesc;
    }

    public City() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityDesc() {
        return cityDesc;
    }

    public void setCityDesc(String cityDesc) {
        this.cityDesc = cityDesc;
    }
}
