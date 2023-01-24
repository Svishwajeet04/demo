package com.example.demo.services;

import com.example.demo.models.User;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface UserService {
    Mono<ResponseEntity<User>> registerUser(User user);

    Mono<ResponseEntity<Map<String, String>>> loginUser(User user);

    Mono<ResponseEntity<User>> getUserDetails(Long id);
}
