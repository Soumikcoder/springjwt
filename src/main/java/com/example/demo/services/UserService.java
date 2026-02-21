package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.MyUserDetails;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User registerUser(User user) {
        if (!userRepo.existsByUsername(user.getUsername())) {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(new MyUserDetails(user));
        }
        return user;
    }

    public boolean isExistsUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public boolean isCorrectCredential(User user, AuthenticationManager authenticationManager) {
        if (userRepo.existsByUsername(user.getUsername())) {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            if (auth.isAuthenticated()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user;
        try {
            System.out.println("Username: " + username);
            user = userRepo.findByUsername(username).get();
            System.out.println("User: " + user);

        } catch (Exception e) {
            System.out.println("Error: " + e);
            throw new UsernameNotFoundException(username + " not found!");
        }

        return user;
    }

}
