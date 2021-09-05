package com.weathertech.weather.api.repo;

import com.weathertech.weather.api.models.ActualWeather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ActualWeatherRepo extends ReactiveCrudRepository<ActualWeather, Long> {

    Mono<ActualWeather> findByCityId(Long cityId);
}
