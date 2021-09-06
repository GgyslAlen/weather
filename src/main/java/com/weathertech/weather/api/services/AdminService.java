package com.weathertech.weather.api.services;

import com.weathertech.weather.api.dto.RestResponse;
import com.weathertech.weather.api.exceptions.RestException;
import com.weathertech.weather.api.models.ActualWeather;
import com.weathertech.weather.api.models.City;
import com.weathertech.weather.api.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

import static com.weathertech.weather.api.models.mapping.SqlMappings.GET_SUB_DET_MAPPING;

@Service
public class AdminService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private ActualWeatherRepo actualWeatherRepo;

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private RoleRepo roleRepo;

    public Mono<RestResponse> userList() {
        return userRepo.findAll().collectList().flatMap(it -> Mono.just(new RestResponse(it)));
    }

    public Mono<RestResponse> subscriptionDetails(Long userId) {
        return subscriptionRepo.getSubDetails(databaseClient, GET_SUB_DET_MAPPING, userId).collectList().flatMap(it -> Mono.just(new RestResponse(it)));
    }

    public Mono<RestResponse> editUser(Long userId, String fullName, Long roleId) throws RestException {
        return userRepo.findById(userId)
                .flatMap(user -> {
                    if (roleId != null)
                        roleRepo.findById(roleId).switchIfEmpty(Mono.error(new RestException(new RestResponse(404, "Role not found")))).subscribe();
                    if (fullName != null)
                        user.setFullname(fullName);
                    if (roleId != null)
                        user.setRoleId(roleId);
                    userRepo.save(user).subscribe();
                    return Mono.just(RestResponse.OK);
                })
                .switchIfEmpty(Mono.defer(() -> Mono.just(new RestResponse(404, "User not found"))));
    }

    public Mono<RestResponse> editCity(Long cityId, String name, String cityDesc) throws RestException {
        if (cityId != null) {
            return cityRepo.findById(cityId).flatMap(city -> {
                city.setName(name);
                city.setCityDesc(cityDesc);
                cityRepo.save(city).subscribe();
                return Mono.just(RestResponse.OK);
            }).switchIfEmpty(Mono.error(new RestException(new RestResponse(404, "City not found")))).onErrorResume(e -> Mono.just(((RestException) e).getRestError()));
        } else {
            City city = new City();
            city.setName(name);
            city.setCityDesc(cityDesc);
            cityRepo.save(city).subscribe();
            return Mono.just(RestResponse.OK);
        }
    }

    public Mono<RestResponse> updateCityWeather(Long cityId, BigDecimal degreesCelsius, Integer windSpeedMPS) throws RestException {
        return cityRepo.findById(cityId).flatMap(city -> actualWeatherRepo.findByCityId(city.getId()).flatMap(actualWeather -> {
            actualWeather.setDegreesCelsius(degreesCelsius);
            actualWeather.setWindSpeedMPS(windSpeedMPS);
            actualWeatherRepo.save(actualWeather).subscribe();
            return Mono.just(RestResponse.OK);
        }).switchIfEmpty(Mono.defer(() -> {
            ActualWeather actualWeather = new ActualWeather();
            actualWeather.setDegreesCelsius(degreesCelsius);
            actualWeather.setWindSpeedMPS(windSpeedMPS);
            actualWeather.setCityId(cityId);
            actualWeatherRepo.save(actualWeather).subscribe();
            return Mono.just(RestResponse.OK);
        }))).switchIfEmpty(Mono.defer(() -> Mono.just(new RestResponse(404, "City not found"))));
    }

}
