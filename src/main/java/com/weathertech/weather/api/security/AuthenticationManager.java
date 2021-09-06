package com.weathertech.weather.api.security;

import com.weathertech.weather.api.utils.SecurityUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final SecurityUtil securityUtil;

    public AuthenticationManager(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        Mono<UsernamePasswordAuthenticationToken> user = null;

        try {
            user = securityUtil.getAuthentication(authToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return user.cast(Authentication.class);
    }
}
