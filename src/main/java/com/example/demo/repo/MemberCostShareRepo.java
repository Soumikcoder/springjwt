package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.MemberCostShare;

@Repository
public interface MemberCostShareRepo extends JpaRepository<MemberCostShare, Long> {
    
}
