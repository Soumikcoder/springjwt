package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repo.UserRepo;


@RestController
public class HelloController {
    @Autowired
    UserRepo userRepo;
    @GetMapping("hello")
    public String hello(Authentication auth){
        return "Hello "+auth.getName();       
    }
}
