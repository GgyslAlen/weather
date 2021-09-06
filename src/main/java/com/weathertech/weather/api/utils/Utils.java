package com.weathertech.weather.api.utils;

import com.google.gson.JsonParser;
import com.weathertech.weather.api.dto.RestResponse;
import com.weathertech.weather.api.exceptions.RestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Utils {

    public static final String STANDART_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final int MIN_PASSWD_LEN = 8;


    private static SimpleDateFormat sdf = new SimpleDateFormat(STANDART_DATE_FORMAT);

    public static void passRegistrationFields(String username, String pass) throws RestException {
        if (pass.length() < MIN_PASSWD_LEN)
            throw new RestException(new RestResponse(100, "Password too short"));

        if (username.length() < 5 || username.indexOf("@") < 0 || username.indexOf(".") < 0)
            throw new RestException(new RestResponse(1, "login is not email"));
    }

    public static Date getSafeDate(Map<String, Object> payload, String name, Date defVal) {
        try {
            return sdf.parse((String) payload.get(name));
        } catch (Exception e){
            return defVal;
        }
    }

    public static String getSafeJson(Map<String, Object> payload, String name, String defVal) {
        try {
            return ((String) payload.get(name)).isEmpty() ? null : (String) payload.get(name);
        } catch (Exception e){
            return defVal;
        }
    }

    /**
     *     */
    public static boolean getSafeBoolean(Map<String, Object> payload, String name, boolean defVal) {
        try {
            return (Boolean) payload.get(name);
        } catch (Exception e) {
            return defVal;
        }
    }

    public static Boolean getSafeBoolean(Map<String, Object> payload, String name, Boolean defVal) {
        try {
            return (Boolean) payload.get(name);
        } catch (Exception e) {
            return defVal;
        }
    }

    /**
     *     */
    public static String getSafeString(Map<String, Object> payload, String name, String defVal) {
        try {
            return (String) payload.get(name);
        } catch (Exception e) {
            return defVal;
        }
    }

    public static int getSafeInt(Map<String, Object> payload, String name, int defVal) {
        try {
            return (int) payload.get(name);
        } catch (Exception e) {
            return defVal;
        }
    }

    public static Integer getSafeInt(Map<String, Object> payload, String name, Integer defVal) {
        try {
            return (Integer) payload.get(name);
        } catch (Exception e) {
            return defVal;
        }
    }

    public static Long getSafeLong(Map<String, Object> payload, String name, Long defVal) {
        try {
            return Long.valueOf(String.valueOf(payload.get(name)));
        } catch (Exception e) {
            return defVal;
        }
    }

    public static BigDecimal getSafeBigDecimal(Map<String, Object> payload, String name, BigDecimal defVal) {
        try {
            return BigDecimal.valueOf((Double) payload.get(name));
        } catch (Exception e) {
            return defVal;
        }
    }

    public static Double getSafeDouble(Map<String, Object> payload, String name, Double defVal) {
        try {
            return (Double) payload.get(name);
        } catch (Exception e) {
            return defVal;
        }
    }

    public static Mono<RestResponse> createResponseRest(RestResponse response) {
        return Mono.just(response);
    }

    public static <T> Flux<ResponseEntity<T>> createResponse(Flux<T> fluxObj) {
        return fluxObj.flatMap(obj -> Flux.just(createResponse(obj, HttpStatus.OK)));
    }

    public static <T> Mono<ResponseEntity<T>> createResponse(Mono<T> monoObj) {
        return monoObj.flatMap(obj -> Mono.just(createResponse(obj, HttpStatus.OK)));
    }

    public static <T> Mono<ResponseEntity<T>> createResponse(T object) {
        return Mono.just(createResponse(object, HttpStatus.OK));
    }

    public static <T> ResponseEntity<T> createResponse(T object, HttpStatus status) {
        JsonParser parser = new JsonParser();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            return new ResponseEntity<T>(object, responseHeaders, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
