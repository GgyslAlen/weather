package com.weathertech.weather.api.models;

import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.Embeddable;
import java.io.Serializable;

public class SubscriptionId implements Serializable {

    @Column("city_id")
    private Long cityId;

    @Column("user_id")
    private Long userId;

    public SubscriptionId() {

    }

    public SubscriptionId(Long cityId, Long userId) {
        this.cityId = cityId;
        this.userId = userId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
