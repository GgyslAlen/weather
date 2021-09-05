package com.weathertech.weather.api.models.mapping;

import com.weathertech.weather.api.dto.SubscriptionDetails;
import com.weathertech.weather.api.dto.WeatherReply;
import com.weathertech.weather.api.models.User;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.BiFunction;

public class SqlMappings {

    public static final BiFunction<Row, RowMetadata, SubscriptionDetails> GET_SUB_DET_MAPPING = (row, rowMetaData) -> new SubscriptionDetails(
            row.get("username", String.class),
            row.get("subscriptionCityName", String.class),
            row.get("subTime", LocalDateTime.class));

    public static final BiFunction<Row, RowMetadata, WeatherReply> WEATHER_MAPPING = (row, rowMetaData) -> new WeatherReply(
            row.get("cityName", String.class),
            row.get("degreesCelsius", BigDecimal.class),
            row.get("windSpeedMPS", Integer.class));

    public static final BiFunction<Row, RowMetadata, User> USER_MAPPING = (row, rowMetaData) -> new User(
            row.get("id", Long.class),
            row.get("login", String.class),
            row.get("password", String.class),
            row.get("fullname", String.class),
            row.get("roleId", Long.class),
            row.get("roleName", String.class));
}
