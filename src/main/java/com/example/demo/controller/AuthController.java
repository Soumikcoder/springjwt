package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RefreshToken;
import com.example.demo.model.RefreshTokenWrapper;
import com.example.demo.model.User;
import com.example.demo.repo.RefreshTokenRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.services.JwtService;
import com.example.demo.services.TokenService;
import com.example.demo.services.UserService;

@RestController
public class AuthController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    TokenService tokenService;
    

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        System.out.println(user);
        if (userService.isCorrectCredential(user, authenticationManager)) {
            String jwtRefreshToken = tokenService.generateRefreshToken(user.getUsername()); 
            String jwtAccessToken = tokenService.generateAccessToken(jwtRefreshToken);
            return ResponseEntity.ok(
                    Map.of("accessToken", jwtAccessToken, "refreshToken", jwtRefreshToken));
        }
        return new ResponseEntity<>("Login failed!", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> addUser(@RequestBody User user) {
        if (!userRepo.existsByUsername(user.getUsername())) {
            userService.registerUser(user);
             String jwtRefreshToken = tokenService.generateRefreshToken(user.getUsername()); 
            String jwtAccessToken = tokenService.generateAccessToken(jwtRefreshToken);
            
            return new ResponseEntity<>(
                    Map.of("accessToken", jwtAccessToken, "refreshToken", jwtRefreshToken), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody RefreshTokenWrapper tokenWrapper) {
        try {
            String refreshToken = tokenWrapper.refreshToken;
            
            if (
                tokenService.isValidRefreshToken(refreshToken) 
            ) {
                String username = tokenService.getUsernameFromRefreshToken(refreshToken);
                String newRefreshToken = tokenService.generateRefreshToken(username);
                String accessToken = tokenService.generateAccessToken(newRefreshToken);
                return ResponseEntity.ok(
                        Map.of("accessToken", accessToken, "refreshToken", newRefreshToken));
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    public ResponseEntity<String> logout(Authentication authentication) {
        try {
            tokenService.invalidateRefreshToken(authentication.getName());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
    }
}
