package com.example.demo.services;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.AppException;
import com.example.demo.model.RefreshToken;
import com.example.demo.model.User;
import com.example.demo.repo.RefreshTokenRepo;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class TokenService {
    RefreshTokenRepo refreshTokenRepo;
    JwtService jwtService;
    UserService userService;
    private Map<String,String> generateTokenByUsername(String username){
        String jwtRefreshToken = generateRefreshToken(username);
        String jwtAccessToken = generateAccessToken(jwtRefreshToken);
        return  Map.of("accessToken", jwtAccessToken, "refreshToken", jwtRefreshToken);
    }

    public Map<String,String> generateToken(User user){
        if (user==null) {
            throw new AppException("User can not be null!", HttpStatus.BAD_REQUEST);
        }
        return generateTokenByUsername(user.getUsername());
    }
    public Map<String,String> generateToken(String oldRefreshToken){
        if (!isValidRefreshToken(oldRefreshToken)) {
            throw new AppException("Invalid refresh token!", HttpStatus.BAD_REQUEST);
        }
        String username = getUsernameFromRefreshToken(oldRefreshToken);
        return  generateTokenByUsername(username);
    }
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
        throw new AppException("Invalid refresh token", HttpStatus.BAD_REQUEST);

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
