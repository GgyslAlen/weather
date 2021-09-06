package com.weathertech.weather.api.services;

import com.weathertech.weather.api.dto.RestResponse;
import com.weathertech.weather.api.exceptions.RestException;
import com.weathertech.weather.api.models.*;
import com.weathertech.weather.api.repo.*;
import com.weathertech.weather.api.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;
import static com.weathertech.weather.api.models.mapping.SqlMappings.WEATHER_MAPPING;

@Service
public class ApiService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Autowired
    private DatabaseClient databaseClient;

    public Mono<RestResponse> login(String username, String password) {
        return securityUtil.loadUserByLogin(username)
                .flatMap(user -> {
                    if (!bCryptPasswordEncoder.matches(password, user.getPassword()))
                        return Mono.just(new RestResponse(401, "Unauthorized"));
                    return securityUtil.injectAuthToken(user).flatMap(it -> Mono.just(new RestResponse(it)));
                })
                .switchIfEmpty(Mono.defer(() -> Mono.
                        just(new RestResponse(404, "User not found"))));
    }

    public Mono<RestResponse> register(String username, String password, String fullname) throws RestException {
        return securityUtil.loadUserByLogin(username)
                .flatMap(monoUser -> Mono
                        .just(new RestResponse(401, "User already exists")))
                .switchIfEmpty(Mono.defer(() -> {
            User user = new User();
            user.setLogin(username);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setFullname(fullname);
            return roleRepo.findByName(Role.ROLE_USER).flatMap(it -> {
                user.setRoleId(it.getId());
                userRepo.save(user).subscribe();
                return securityUtil.injectAuthToken(user).flatMap(tok -> Mono.just(new RestResponse(tok)));
            });
        }));
    }

    public Mono<RestResponse> citiesList() {
        return cityRepo.findAll()
                .collectList()
                .flatMap(it -> Mono.just(new RestResponse(it)));
    }

    public Mono<RestResponse> subscribeToCity(Long cityToSubscribe, Principal principal) {
        return securityUtil
                .loadUserByLogin(principal.getName())
                .flatMap(user -> cityRepo.findById(cityToSubscribe)
                .flatMap(city -> subscriptionRepo.findByCityIdAndUserId(cityToSubscribe, user.getId())
                        .flatMap(sub -> Mono.just(new RestResponse(102, "User already subscribed to this city")))
                        .switchIfEmpty(Mono.defer(() -> {
                            Subscription subscription = new Subscription(user.getId(), cityToSubscribe);
                            subscriptionRepo.save(subscription).subscribe();
                            return Mono.just(RestResponse.OK);
                        })))
                        .switchIfEmpty(Mono.defer(() -> Mono.just(new RestResponse(404, "City not found")))));
    }

    public Mono<RestResponse> unsubscribe(Long cityToUnsubscribe, Principal principal) {
        return securityUtil
                .loadUserByLogin(principal.getName())
                .flatMap(user -> cityRepo.findById(cityToUnsubscribe)
                .flatMap(city -> subscriptionRepo.findByCityIdAndUserId(cityToUnsubscribe, user.getId())
                        .flatMap(sub -> {
                            sub.setActive(false);
                            subscriptionRepo.save(sub).subscribe();
                            return Mono.just(RestResponse.OK);
                        }).switchIfEmpty(Mono.defer(() -> Mono.just(new RestResponse(404, "Subscription not found"))))));
    }

    public Mono<RestResponse> updateCityWeather(Principal principal) {
        return securityUtil
                .loadUserByLogin(principal.getName())
                .flatMap(user -> subscriptionRepo
                        .getSubscribedCitiesWeather(databaseClient, WEATHER_MAPPING, user.getId())
                        .collectList().flatMap(it -> Mono.just(new RestResponse(it))));
    }

}

