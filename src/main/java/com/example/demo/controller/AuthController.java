package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RefreshTokenWrapper;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.services.JwtService;
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
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        System.out.println(user);
        if (userService.isCorrectCredential(user, authenticationManager)) {
            String jwtAccessToken = jwtService.generateAccessToken(user.getUsername());
            String jwtRefreshToken = jwtService.generateRefreshToken(user.getUsername());
            return ResponseEntity.ok(
                    Map.of("accessToken", jwtAccessToken, "refreshToken", jwtRefreshToken));
        }
        return new ResponseEntity<>("Login failed!", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> addUser(@RequestBody User user) {
        if (!userRepo.existsByUsername(user.getUsername())) {
            userService.registerUser(user);
            String jwtAccessToken = jwtService.generateAccessToken(user.getUsername());
            String jwtRefreshToken = jwtService.generateRefreshToken(user.getUsername());
            return new ResponseEntity<>(
                    Map.of("accessToken", jwtAccessToken, "refreshToken", jwtRefreshToken), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody RefreshTokenWrapper tokenWrapper) {
        try {
            String refreshToken = tokenWrapper.refreshToken;
            String username = jwtService.extractUsername(refreshToken);
            UserDetails userDetails = userRepo.findByUsername(username).get();
            if (jwtService.validateToken(userDetails, refreshToken)) {
                String accessToken = jwtService.generateAccessToken(username);
                String newRefreshToken = jwtService.generateRefreshToken(username);
                return ResponseEntity.ok(
                        Map.of("accessToken", accessToken, "refreshToken", newRefreshToken));
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
