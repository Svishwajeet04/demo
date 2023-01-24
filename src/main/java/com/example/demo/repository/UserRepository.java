package com.example.demo.repository;

import com.example.demo.models.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByEmail(String email);

    @Query("""
            select * from users where email = :email and password = :encode
            """)
    Mono<User> findByEmailAndPassword(String email, String encode);
}
