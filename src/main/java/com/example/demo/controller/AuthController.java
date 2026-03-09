package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RefreshTokenDTO;
import com.example.demo.model.User;
import com.example.demo.services.TokenService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        if (userService.isCorrectCredential(user, authenticationManager)) {
            return ResponseEntity.ok(tokenService.generateToken(user));
        }
        return new ResponseEntity<>("Login failed!", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> addUser(@RequestBody User user) {
        if (!userService.isExistsUsername(user.getUsername())) {
            userService.registerUser(user);
            return new ResponseEntity<>(tokenService.generateToken(user), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody RefreshTokenDTO tokenWrapper) {
        String refreshToken = tokenWrapper.refreshToken; 
        return ResponseEntity.ok(tokenService.generateToken(refreshToken));

    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(Authentication authentication) {
        tokenService.invalidateRefreshToken(authentication.getName());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
