package com.weathertech.weather.api;

import com.weathertech.weather.api.dto.RestResponse;
import com.weathertech.weather.api.dto.SubscriptionDetails;
import com.weathertech.weather.api.exceptions.RestException;
import com.weathertech.weather.api.models.City;
import com.weathertech.weather.api.models.User;
import com.weathertech.weather.api.services.AdminService;
import com.weathertech.weather.api.services.ApiService;
import com.weathertech.weather.api.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;

import static com.weathertech.weather.api.utils.Utils.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private AdminService adminService;

    private ApiService apiService;

    @Autowired
    public AdminController(AdminService adminService, ApiService apiService) {
        this.adminService = adminService;
        this.apiService = apiService;
    }

    //Method to get all users
    @RequestMapping(value = "/user-list", method = RequestMethod.GET)
    public Mono<ResponseEntity<RestResponse>> userList() {
        return createResponse(adminService.userList());
    }

    //Method to get active subscriptions of selected users, or all users if userId params is not null
    @RequestMapping(value = "/user-details", method = RequestMethod.GET)
    public Mono<ResponseEntity<RestResponse>> userDetails(
            @RequestParam(name = "userId", required = false) Long userId
    ) {
        return createResponse(adminService.subscriptionDetails(userId));
    }

    //Edit user method
    @RequestMapping(value = "/edit-user", method = RequestMethod.POST)
    public Mono<ResponseEntity<RestResponse>> editUser(
            @RequestBody Map<String, Object> requestPayload
    ) {
        Long userId = getSafeLong(requestPayload, "userId", null);
        String fullname = getSafeString(requestPayload, "fullname", null);
        Long roleId = getSafeLong(requestPayload, "roleId", null);
        if (userId == null || (fullname == null && roleId == null))
            return createResponse(RestResponse.ERROR_WRONG_PARAMETERS);
        try {
            return createResponse(adminService.editUser(userId, fullname, roleId));
        } catch (RestException e) {
            return createResponse(e.getRestError());
        }
    }

    //Get all cities
    @RequestMapping(value = "/cities-list", method = RequestMethod.GET)
    public Mono<ResponseEntity<RestResponse>> citiesList() {
        return createResponse(apiService.citiesList());
    }

    //Edit city method
    @RequestMapping(value = "/edit-city", method = RequestMethod.POST)
    public Mono<ResponseEntity<RestResponse>> editCity(
            @RequestBody Map<String, Object> requestPayload
    ) {
        Long cityId = getSafeLong(requestPayload, "cityId", null);
        String name = getSafeString(requestPayload, "name", null);
        String cityDesc = getSafeString(requestPayload, "cityDesc", null);
        if ((cityId == null && (name == null || cityDesc == null)) || (cityId != null && (name == null && cityDesc == null)))
            return createResponse(RestResponse.ERROR_WRONG_PARAMETERS);
        try {
            return createResponse(adminService.editCity(cityId, name, cityDesc));
        } catch (RestException e) {
            return createResponse(e.getRestError());
        }
    }

    //Method for updating weather for selected city
    @RequestMapping(value = "/update-city-weather", method = RequestMethod.POST)
    public Mono<ResponseEntity<RestResponse>> updateCityWeather(
            @RequestBody Map<String, Object> requestPayload
    ) {
        Long cityId = getSafeLong(requestPayload, "cityId", null);
        BigDecimal degreesCelsius = getSafeBigDecimal(requestPayload, "degreesCelsius", null);
        Integer windSpeedMPS = getSafeInt(requestPayload, "windSpeedMPS", null);
        if (cityId == null || degreesCelsius == null || windSpeedMPS == null)
            return createResponse(RestResponse.ERROR_WRONG_PARAMETERS);
        try {
            return createResponse(adminService.updateCityWeather(cityId, degreesCelsius, windSpeedMPS));
        } catch (RestException e) {
            return createResponse(e.getRestError());
        }
    }

}
