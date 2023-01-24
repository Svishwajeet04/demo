package com.example.demo.utils;


import com.example.demo.models.AuthPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthUtil {

    public Mono<AuthPrincipal> getAuthPrincipalFromSecurityContext() {
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication).map(a -> (AuthPrincipal) a.getPrincipal());
    }

    public Mono<String> getUserEmailFromSecurityContext() {
        return getAuthPrincipalFromSecurityContext().map(AuthPrincipal::getEmail);
    }

    public Mono<String> getUserNameFromSecurityContext() {
        return getAuthPrincipalFromSecurityContext().map(AuthPrincipal::getName);
    }

    public Mono<Long> getUserIdFromSecurityContext() {
      return getAuthPrincipalFromSecurityContext().map(AuthPrincipal::getUserId);
    }

}
