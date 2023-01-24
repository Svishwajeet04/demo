package com.example.demo.services.impl;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.JwtService;
import com.example.demo.services.UserService;
import com.example.demo.utils.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    private AuthUtil authUtil;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    @Override
    public Mono<ResponseEntity<User>> registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user)
                .map(a -> new ResponseEntity<>(user, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Override
    public Mono<ResponseEntity<Map<String, String>>> loginUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .filter(a-> passwordEncoder.matches(user.getPassword() , a.getPassword()))
                .map(foundUser -> new ResponseEntity<>(jwtService.createJwtToken(foundUser) , HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Override
    public Mono<ResponseEntity<User>> getUserDetails(Long id) {
        return userRepository.findById(id)
                .zipWith(authUtil.getUserIdFromSecurityContext())
                .filter(a-> Objects.equals(a.getT1().getId(), id))
                .map(a-> new ResponseEntity<>(a.getT1() , HttpStatus.OK ))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
