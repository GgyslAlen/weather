package com.weathertech.weather;

import com.google.gson.JsonObject;
import com.weathertech.weather.api.AdminController;
import com.weathertech.weather.api.ApiController;
import com.weathertech.weather.api.models.*;
import com.weathertech.weather.api.repo.*;
import com.weathertech.weather.api.services.AdminService;
import com.weathertech.weather.api.services.ApiService;
import com.weathertech.weather.api.utils.SecurityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(controllers = {AdminController.class, ApiController.class})
@Import({AdminService.class, ApiService.class, SecurityUtil.class})
public class WeatherAdminTest {

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

    @Test
    @WithMockUser(username = USER_LOGIN, password = USER_PASSWORD)
    void userListTest() {
        when(userRepo.findAll()).thenReturn(Flux.just(DEFAULT_USER));
        webTestClient.mutateWith(csrf()).get()
                .uri("/admin/user-list")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.error").isEqualTo(0);
    }

    @Test
    @WithMockUser(username = USER_LOGIN, password = USER_PASSWORD)
    void userDetailsTest() {
        when(subscriptionRepo.getSubDetails(any(DatabaseClient.class), any(BiFunction.class), nullable(Long.class))).thenReturn(Flux.just(DEFAULT_SUB_DETAILS));
        webTestClient.mutateWith(csrf()).get()
                .uri("/admin/user-details")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.error").isEqualTo(0);
    }

    @Test
    @WithMockUser(username = USER_LOGIN, password = USER_PASSWORD)
    void editUserTest() {
        when(userRepo.findById(any(Long.class))).thenReturn(Mono.just(DEFAULT_USER));
        when(roleRepo.findById(any(Long.class))).thenReturn(Mono.just(DEFAULT_ROLE));
        when(userRepo.save(any(User.class))).thenReturn(Mono.just(DEFAULT_USER));
        JsonObject body = new JsonObject();
        body.addProperty("userId", DEFAULT_USER.getId());
        body.addProperty("fullname", "The test");
        webTestClient.mutateWith(csrf())
                .post()
                .uri("/admin/edit-user")
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
    void cityListTest() {
        when(cityRepo.findAll()).thenReturn(Flux.just(DEFAULT_CITY));
        webTestClient.mutateWith(csrf()).get()
                .uri("/admin/cities-list")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.error").isEqualTo(0);
    }

    @Test
    @WithMockUser(username = USER_LOGIN, password = USER_PASSWORD)
    void editCityTest() {
        when(cityRepo.findById(any(Long.class))).thenReturn(Mono.empty());
        when(cityRepo.save(any(City.class))).thenReturn(Mono.just(DEFAULT_CITY));
        JsonObject body = new JsonObject();
        body.addProperty("name", "Samarkand");
        body.addProperty("cityDesc", "The test");
        webTestClient.mutateWith(csrf())
                .post()
                .uri("/admin/edit-city")
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
        when(cityRepo.findById(any(Long.class))).thenReturn(Mono.just(DEFAULT_CITY));
        when(actualWeatherRepo.findByCityId(DEFAULT_CITY.getId())).thenReturn(Mono.just(DEFAULT_ACTUAL_WEATHER));
        when(actualWeatherRepo.save(any(ActualWeather.class))).thenReturn(Mono.just(DEFAULT_ACTUAL_WEATHER));
        JsonObject body = new JsonObject();
        body.addProperty("cityId", DEFAULT_CITY.getId());
        body.addProperty("degreesCelsius", 13.2);
        body.addProperty("windSpeedMPS", 9);
        webTestClient.mutateWith(csrf())
                .post()
                .uri("/admin/update-city-weather")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body.toString()))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.error").isEqualTo(0);
    }


}
