package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ExpenseGroup;

@Repository
public interface ExpenseGroupRepo extends JpaRepository<ExpenseGroup, Long> {

    boolean existsByGroupName(String groupName);

}