package com.weathertech.weather.api.security;

public class SecurityConstants {
    public static final String SECRET = "Secret Ravnaq Soltik";
    public static final long EXPIRATION_TIME = 3_600_000; // 60 mins

    public static final String TOKEN_TYPE = "Bearer";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/sign_up";

    public static final String ALTERNATIVE_REQUEST_TOKEN_PARAM = "paramedHash";
    
    // Максимальное число попыток ввода ПИ-кода подряд после чего произойдет блокировка регистрации
    public static final int MAX_PIN_AUTH_ATTEMPTS = 5;
}
