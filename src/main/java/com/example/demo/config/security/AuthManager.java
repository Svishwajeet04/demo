package com.example.demo.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.models.AuthPrincipal;
import com.example.demo.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Slf4j
@Component
@AllArgsConstructor
public class AuthManager implements ReactiveAuthenticationManager {

    private UsernamePasswordAuthenticationToken verifyJWTAndGetAuthenticationToken(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm()).withIssuer(Constants.DEMO_DOMAIN).build().verify(token);
        String email = decodedJWT.getClaim(Constants.EMAIL).asString();
        String name = decodedJWT.getClaim(Constants.NAME).asString();
        Long userId = decodedJWT.getClaim(Constants.USER_ID).asLong();
        final String role = "user";
        Collection<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(role));
        log.debug("{}", Arrays.toString(authorities.toArray()));
        AuthPrincipal principal = buildCurrentUserAuthPrincipal(userId, email, role, name);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private AuthPrincipal buildCurrentUserAuthPrincipal(Long userId, String email, String role, String name) {
        return AuthPrincipal.builder().userId(userId).email(email).role(role).name(name).build();
    }

    /**
     * HmacSHA256 algorithm to encrypt our created jwt token with the secret key given
     *
     * @return Algorithm bean
     */
    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256("secretKey");
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication).cast(BearerToken.class)
                .flatMap(auth -> {
                    UsernamePasswordAuthenticationToken token =  verifyJWTAndGetAuthenticationToken(auth.getCredentials());
                    return Mono.just(token);
                });
    }
}
