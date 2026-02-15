package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ExpenseGroup;

public interface ExpenseGroupRepo extends JpaRepository<ExpenseGroup, Long> {

}