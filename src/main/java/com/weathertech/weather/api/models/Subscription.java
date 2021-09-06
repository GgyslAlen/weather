package com.weathertech.weather.api.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table("subscriptions")
public class Subscription implements Serializable {

    @Id
    private Long id;

    @Column("city_id")
    private Long cityId;

    @Column("user_id")
    private Long userId;

    @Column("subscription_time")
    private LocalDateTime subscriptionTime;

    @Column("active")
    private Boolean active;


    public Subscription() {

    }

    public Subscription(Long userId, Long cityId) {
        this.userId = userId;
        this.cityId = cityId;
        this.subscriptionTime = LocalDateTime.now();
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getSubscriptionTime() {
        return subscriptionTime;
    }

    public void setSubscriptionTime(LocalDateTime subscriptionTime) {
        this.subscriptionTime = subscriptionTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
