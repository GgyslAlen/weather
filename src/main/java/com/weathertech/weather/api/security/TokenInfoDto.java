package com.weathertech.weather.api.security;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.weathertech.weather.api.security.SecurityConstants.EXPIRATION_TIME;
import static com.weathertech.weather.api.security.SecurityConstants.TOKEN_TYPE;

public class TokenInfoDto {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String access_token;
    private String token_type;
    private long expires_in;
    private Date expires_at;
    private Object user;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getExpires_at() {
        return sdf.format(expires_at);
    }

    public void setExpires_at(Date expires_at) {
        this.expires_at = expires_at;
    }

    public TokenInfoDto(String access_token, Date expires_at, Object user) {
        init();
        this.access_token = access_token;
        this.expires_at = expires_at;
        this.user = user;
    }

    public TokenInfoDto() {
        init();
    }

    private void init() {
        token_type = TOKEN_TYPE;
        expires_in = EXPIRATION_TIME / 1000L;   // ms -> sec.
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }
}
