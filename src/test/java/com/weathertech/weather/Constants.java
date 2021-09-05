package com.weathertech.weather;

import com.weathertech.weather.api.dto.SubscriptionDetails;
import com.weathertech.weather.api.dto.WeatherReply;
import com.weathertech.weather.api.models.*;

import java.math.BigDecimal;

public class Constants {

    public static final String USER_LOGIN = "alenovich234@gmail.com";
    public static final String USER_PASSWORD = "2783200a";
    public static final User DEFAULT_USER = new User(1L, USER_LOGIN, USER_PASSWORD, "Test Testov", 1L);
    public static final City DEFAULT_CITY = new City(1L, "Tashkent", "test");
    public static final Subscription DEFAULT_SUBSCRIPTION = new Subscription(DEFAULT_USER.getId(), DEFAULT_CITY.getId());
    public static final SubscriptionDetails DEFAULT_SUB_DETAILS = new SubscriptionDetails(DEFAULT_USER.getLogin(), DEFAULT_CITY.getName(), DEFAULT_SUBSCRIPTION.getSubscriptionTime());
    public static final Role DEFAULT_ROLE = new Role(1L, Role.ROLE_ADMIN, "Role admin");
    public static final ActualWeather DEFAULT_ACTUAL_WEATHER = new ActualWeather(1L, DEFAULT_CITY.getId(), BigDecimal.valueOf(18.5), 13);
    public static final WeatherReply DEFAULT_WEATHER = new WeatherReply(DEFAULT_CITY.getName(), BigDecimal.valueOf(33.4), 17);

}
