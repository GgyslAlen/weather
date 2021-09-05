package com.weathertech.weather;

import com.google.gson.JsonObject;
import com.weathertech.weather.api.AdminController;
import com.weathertech.weather.api.ApiController;
import com.weathertech.weather.api.models.Subscription;
import com.weathertech.weather.api.repo.*;
import com.weathertech.weather.api.services.AdminService;
import com.weathertech.weather.api.services.ApiService;
import com.weathertech.weather.api.utils.SecurityUtil;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

import static com.weathertech.weather.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(controllers = {AdminController.class, ApiController.class})
@Import({AdminService.class, ApiService.class, SecurityUtil.class})
class WeatherApplicationTests {

    @MockBean
    private UserRepo userRepo;

    @InjectMocks
    private ApiService apiService;

    @InjectMocks
    private SecurityUtil securityUtil;

    @MockBean
    private RoleRepo roleRepo;

    @MockBean
    private SubscriptionRepo subscriptionRepo;

    @MockBean
    private CityRepo cityRepo;

    @MockBean
    private ActualWeatherRepo actualWeatherRepo;

    @MockBean
    private DatabaseClient databaseClient;

    @Autowired
    private WebTestClient webTestClient;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(username = USER_LOGIN, password = USER_PASSWORD)
    void cityListTest() {
        when(cityRepo.findAll()).thenReturn(Flux.just(DEFAULT_CITY));
        webTestClient.mutateWith(csrf()).get()
                .uri("/api/cities-list")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.error").isEqualTo(0);
    }

    @Test
    @WithMockUser(username = USER_LOGIN, password = USER_PASSWORD)
    void subscribeToCityTest() {
        when(userRepo.findByLogin(any(DatabaseClient.class), any(BiFunction.class), any())).thenReturn(Mono.just(DEFAULT_USER));
        when(cityRepo.findById(any(Long.class))).thenReturn(Mono.just(DEFAULT_CITY));
        when(subscriptionRepo.findByCityIdAndUserId(any(), any())).thenReturn(Mono.empty());
        when(subscriptionRepo.save(any(Subscription.class))).thenReturn(Mono.just(DEFAULT_SUBSCRIPTION));
        JsonObject body = new JsonObject();
        body.addProperty("cityToSubscribe", DEFAULT_CITY.getId());
        webTestClient.mutateWith(csrf())
                .post()
                .uri("/api/subscribe-to-city")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body.toString()))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.error").isEqualTo(0);
    }

    @Test
    @WithMockUser(username = USER_LOGIN, password = USER_PASSWORD)
    void unsubscribeTest() {
        when(userRepo.findByLogin(any(DatabaseClient.class), any(BiFunction.class), any())).thenReturn(Mono.just(DEFAULT_USER));
        when(cityRepo.findById(any(Long.class))).thenReturn(Mono.just(DEFAULT_CITY));
        when(subscriptionRepo.findByCityIdAndUserId(any(), any())).thenReturn(Mono.just(DEFAULT_SUBSCRIPTION));
        when(subscriptionRepo.save(any(Subscription.class))).thenReturn(Mono.just(DEFAULT_SUBSCRIPTION));
        JsonObject body = new JsonObject();
        body.addProperty("cityToUnsubscribe", DEFAULT_CITY.getId());
        webTestClient.mutateWith(csrf())
                .post()
                .uri("/api/unsubscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body.toString()))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.error").isEqualTo(0);
    }

    @Test
    @WithMockUser(username = USER_LOGIN, password = USER_PASSWORD)
    void updateCityWeatherTest() {
        when(userRepo.findByLogin(any(DatabaseClient.class), any(BiFunction.class), any())).thenReturn(Mono.just(DEFAULT_USER));
        when(subscriptionRepo.getSubscribedCitiesWeather(any(DatabaseClient.class), any(BiFunction.class), any(Long.class))).thenReturn(Flux.just(DEFAULT_WEATHER));
        webTestClient.mutateWith(csrf()).get()
                        .uri("/api/update-city-weather")
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody()
                        .jsonPath("$.error").isEqualTo(0);
    }

}
