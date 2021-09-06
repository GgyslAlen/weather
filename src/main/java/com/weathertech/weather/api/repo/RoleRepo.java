package com.weathertech.weather.api.repo;

import com.weathertech.weather.api.models.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoleRepo extends ReactiveCrudRepository<Role, Long> {

    Mono<Role> findByName(String name);

}
