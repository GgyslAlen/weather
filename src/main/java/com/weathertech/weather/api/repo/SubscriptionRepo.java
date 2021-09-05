package com.weathertech.weather.api.repo;

import com.weathertech.weather.api.dto.SubscriptionDetails;
import com.weathertech.weather.api.dto.WeatherReply;
import com.weathertech.weather.api.models.Subscription;
import com.weathertech.weather.api.models.SubscriptionId;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.BiFunction;

@Repository
public interface SubscriptionRepo extends ReactiveCrudRepository<Subscription, Long> {

    default Flux<WeatherReply> getSubscribedCitiesWeather(DatabaseClient databaseClient, BiFunction<Row, RowMetadata, WeatherReply> mapping, Long userId) {
        return databaseClient.sql("SELECT c.name AS cityName, aw.degrees_celsius AS degreesCelsius, aw.wind_speed_mps AS windSpeedMPS FROM subscriptions s LEFT JOIN cities c ON c.id = s.city_id RIGHT JOIN actual_weather aw ON aw.city_id = c.id WHERE s.user_id = :userId AND s.active IS true").bind("userId", userId)
                .map(mapping).all();
    }

    default Flux<SubscriptionDetails> getSubDetails(DatabaseClient databaseClient, BiFunction<Row, RowMetadata, SubscriptionDetails> mapping, Long userId) {
        String sql = "SELECT u.login AS username, c.name AS subscriptionCityName, s.subscription_time AS subTime FROM subscriptions s LEFT JOIN users u ON u.id = s.user_id LEFT JOIN cities c ON c.id = s.city_id WHERE s.active = true";
        DatabaseClient.GenericExecuteSpec genericExecuteSpec = null;
        if (userId != null) {
            sql += " AND u.id = :userId";
            genericExecuteSpec = databaseClient.sql(sql).bind("userId", userId);
        } else
            genericExecuteSpec = databaseClient.sql(sql);
        return genericExecuteSpec.map(mapping).all();
    }

    Mono<Subscription> findByCityIdAndUserId(Long cityId, Long userId);

}
