package com.weathertech.weather.api.repo;

import com.weathertech.weather.api.dto.WeatherReply;
import com.weathertech.weather.api.models.User;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Repository
public interface UserRepo extends ReactiveCrudRepository<User, Long> {

    default Mono<User> findByLogin(DatabaseClient databaseClient, BiFunction<Row, RowMetadata, User> mapping, String login) {
        return databaseClient.sql("SELECT u.id, u.login, u.password, u.fullname, u.role_id AS roleId, r.name AS roleName FROM users u LEFT JOIN roles r ON r.id = u.role_id WHERE u.login = :login").bind("login", login).map(mapping).first();
    }

    Flux<User> findAll();

}
