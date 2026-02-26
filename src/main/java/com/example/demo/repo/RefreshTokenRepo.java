package com.example.demo.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByUserUsernameAndIsExpiredFalse(String username);
    boolean existsByUserUsernameAndTokenAndIsExpiredFalse(String username, String token);
    RefreshToken deleteByUserUsername(String username);
    boolean existsByUserUsername(String username);    
} 
