package com.weathertech.weather.api.utils;

import com.weathertech.weather.api.models.Role;
import com.weathertech.weather.api.models.User;
import com.weathertech.weather.api.repo.RoleRepo;
import com.weathertech.weather.api.repo.UserRepo;
import com.weathertech.weather.api.security.TokenInfoDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.weathertech.weather.api.models.mapping.SqlMappings.USER_MAPPING;
import static com.weathertech.weather.api.security.SecurityConstants.*;

@Service("SecurityUtil")
public class SecurityUtil {

    private final Logger log = LoggerFactory.getLogger(SecurityUtil.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private DatabaseClient databaseClient;

    public Mono<User> loadUserByLogin(String login) {
        return userRepo.findByLogin(databaseClient, USER_MAPPING, login);
    }

    public Mono<UsernamePasswordAuthenticationToken> getAuthentication(String tokenHash)
    {
        if (tokenHash != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws( tokenHash )
                    .getBody()
                    .getSubject();
            return userRepo.findByLogin(databaseClient, USER_MAPPING, user).flatMap(it ->  {
                Set<GrantedAuthority> roles = new HashSet<>();
                roles.add(new SimpleGrantedAuthority(it.getRoleName()));
                return Mono.just(new UsernamePasswordAuthenticationToken(user, null, roles));
            }).switchIfEmpty(Mono.defer(() -> {
                log.error("WARNING: applicationUser for login " + user + " not found.");
                return Mono.empty();
            }));
        }
        return null;
    }

    public Mono<TokenInfoDto> injectAuthToken(User user) {
        log.info("--- user " + user);
        Date expireTime = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        String token = Jwts.builder()
                .setSubject( user.getLogin() )
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();

        return roleRepo.findById(user.getRoleId()).flatMap(roleIt -> Mono.just(new TokenInfoDto(token, expireTime, roleIt)));
    }

    public long getTokenExpirePeriod() {
        return EXPIRATION_TIME;
    }

}
