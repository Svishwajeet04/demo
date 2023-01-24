package com.example.demo.services;

import com.example.demo.models.User;

import java.util.Map;

public interface JwtService {
    Map<String, String> createJwtToken(User user);

}
