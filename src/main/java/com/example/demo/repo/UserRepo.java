package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.model.MyUserDetails;


public interface UserRepo extends JpaRepository<MyUserDetails,Long> {

    Optional<MyUserDetails> findByUsername(String username);

    boolean existsByUsername(String username);

}
