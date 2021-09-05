package com.weathertech.weather.api.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class SubscriptionDetails {

    private String user;

    private String subscriptionCityName;

    private LocalDateTime subscriptionTime;

    public SubscriptionDetails() {

    }

    public SubscriptionDetails(String user, String subscriptionCityName, LocalDateTime subscriptionTime) {
        this.user = user;
        this.subscriptionCityName = subscriptionCityName;
        this.subscriptionTime = subscriptionTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSubscriptionCityName() {
        return subscriptionCityName;
    }

    public void setSubscriptionCityName(String subscriptionCityName) {
        this.subscriptionCityName = subscriptionCityName;
    }

    public LocalDateTime getSubscriptionTime() {
        return subscriptionTime;
    }

    public void setSubscriptionTime(LocalDateTime subscriptionTime) {
        this.subscriptionTime = subscriptionTime;
    }

}
