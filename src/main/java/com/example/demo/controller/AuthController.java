package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
    public ResponseEntity<String> login(@RequestBody User user){
        System.out.println(user);
        if(userService.isCorrectCredential(user,authenticationManager)){
             String jwtToken=jwtService.generateToken(user.getUsername());
            return new ResponseEntity<>(jwtToken,HttpStatus.ACCEPTED);
        }
       return new ResponseEntity<>("Login failed!",HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody User user){
        if (!userRepo.existsByUsername(user.getUsername())) {
            userService.registerUser(user);
            String jwtToken=jwtService.generateToken(user.getUsername());
            return new ResponseEntity<>(jwtToken,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
