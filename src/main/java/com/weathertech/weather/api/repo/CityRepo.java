package com.weathertech.weather.api.repo;

import com.weathertech.weather.api.models.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface CityRepo extends ReactiveCrudRepository<City, Long> {

    Flux<City> findAll();

}
