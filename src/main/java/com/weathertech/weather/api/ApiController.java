package com.weathertech.weather.api;

import com.weathertech.weather.api.dto.RestResponse;
import com.weathertech.weather.api.exceptions.RestException;
import com.weathertech.weather.api.services.ApiService;
import com.weathertech.weather.api.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.security.Principal;
import java.util.Map;

import static com.weathertech.weather.api.utils.Utils.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    private ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    //Authorization method. Returns JWT token
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Mono<ResponseEntity<RestResponse>> login(
            @RequestBody Map<String, Object> requestPayload
    ) {
        String username = getSafeString(requestPayload, "username", null);
        String password = getSafeString(requestPayload, "password", null);
        if (username == null || password == null)
            return createResponse(RestResponse.ERROR_WRONG_PARAMETERS);
        return createResponse(apiService.login(username, password));
    }

    //Registration method. Returns JWT token in response
    /**  */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Mono<ResponseEntity<RestResponse>> register(
            @RequestBody Map<String, Object> requestPayload) {

        Mono<RestResponse> responseMono = null;

        try {
            String username = getSafeString(requestPayload, "username", null);
            String pass = getSafeString(requestPayload, "password", null);
            String fullName = getSafeString(requestPayload, "fullname", null);

            if (username == null || pass == null || fullName == null)
                return createResponse(RestResponse.ERROR_WRONG_PARAMETERS);

            username = username.toLowerCase();

            try {
                Utils.passRegistrationFields(username, pass);
            } catch (RestException e) {
                return createResponse(e.getRestError());
            }

            responseMono = apiService.register(username, pass, fullName);
        }  catch (Exception e) {
            e.printStackTrace();
            return createResponse(RestResponse.ERROR_SERVICE_UNAVAILABLE);
        }

        return createResponse(responseMono);
    }

    //All cities
    @RequestMapping(value = "/cities-list", method = RequestMethod.GET)
    public Mono<ResponseEntity<RestResponse>> citiesList() {
        return createResponse(apiService.citiesList());
    }

    //Subscribe to selected city
    @RequestMapping(value = "/subscribe-to-city", method = RequestMethod.POST)
    public Mono<ResponseEntity<RestResponse>> subscribeToCity(
            @RequestBody Map<String, Object> requestPayload,
            Principal principal
    ) {
        Long cityToSubscribe = getSafeLong(requestPayload, "cityToSubscribe", null);
        if (cityToSubscribe == null)
            return createResponse(RestResponse.ERROR_WRONG_PARAMETERS);
        return createResponse(apiService.subscribeToCity(cityToSubscribe, principal));
    }

    //Unsubscribe from selected city
    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    public Mono<ResponseEntity<RestResponse>> unsubscribe(
            @RequestBody Map<String, Object> requestPayload,
            Principal principal
    ) {
        Long cityToUnsubscribe = getSafeLong(requestPayload, "cityToUnsubscribe", null);
        if (cityToUnsubscribe == null)
            return createResponse(RestResponse.ERROR_WRONG_PARAMETERS);
        return createResponse(apiService.unsubscribe(cityToUnsubscribe, principal));
    }

    //Get actual weather for cities which user subscribed
    @RequestMapping(value = "/update-city-weather", method = RequestMethod.GET)
    public Mono<ResponseEntity<RestResponse>> updateCityWeather(
            Principal principal
    ) {
        return createResponse(apiService.updateCityWeather(principal));
    }

}
