package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.RefreshToken;
import com.example.demo.repo.RefreshTokenRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TokenService {
    @Autowired
    RefreshTokenRepo refreshTokenRepo;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;

    public String generateRefreshToken(String username) {
        if (refreshTokenRepo.existsByUserUsername(username)) {
            refreshTokenRepo.deleteByUserUsername(username);
            refreshTokenRepo.flush();
        }
        String jwtRefreshToken = jwtService.generateRefreshToken(username);
        refreshTokenRepo.save(
                new RefreshToken(
                        jwtRefreshToken,
                        userService.loadUserByUsername(username)));
        return jwtRefreshToken;
    }

    public String generateAccessToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        if (refreshTokenRepo.existsByUserUsernameAndTokenAndIsExpiredFalse(username, refreshToken)) {
            return jwtService.generateAccessToken(username);
        }
        return null;
    }

    public String getUsernameFromRefreshToken(String refreshToken) {
        return jwtService.extractUsername(refreshToken);
    }

    public boolean isValidRefreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        System.out.println(username);
        return refreshTokenRepo.existsByUserUsernameAndTokenAndIsExpiredFalse(username, refreshToken)
                &&
                jwtService.validateToken(userService.loadUserByUsername(username), refreshToken);

    }

    public void invalidateRefreshToken(String username) {
        if (refreshTokenRepo.existsByUserUsername(username)) {
            refreshTokenRepo.deleteByUserUsername(username);
            refreshTokenRepo.flush();
        }
    }
}
