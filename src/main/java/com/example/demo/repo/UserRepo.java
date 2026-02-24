package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.MyUserDetails;

@Repository
public interface UserRepo extends JpaRepository<MyUserDetails,Long> {

    Optional<MyUserDetails> findByUsername(String username);

    boolean existsByUsername(String username);

}
