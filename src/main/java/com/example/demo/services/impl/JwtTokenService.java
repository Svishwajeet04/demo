package com.example.demo.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.models.User;
import com.example.demo.services.JwtService;
import com.example.demo.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtTokenService implements JwtService {
    private Algorithm algorithm;

    @Override
    public Map<String, String> createJwtToken(User user) {
        return Map.of("jwt", generateJWTToken(user),
                "userId", "" + user.getId(),
                "email", user.getEmail(),
                "name", user.getName());
    }

    private String generateJWTToken(User user) {
        return JWT.create()
                .withExpiresAt(getExpiryDateForTokens())
                .withIssuer(Constants.DEMO_DOMAIN)
                .withClaim(Constants.EMAIL, user.getEmail())
                .withClaim(Constants.USER_ID, user.getId())
                .withClaim(Constants.NAME, user.getName())
                .sign(algorithm);
    }

    private Date getExpiryDateForTokens() {
        return new Date(
                ZonedDateTime.now()
                        .plusMinutes(Long.parseLong("500"))
                        .toInstant()
                        .toEpochMilli());
    }

}
