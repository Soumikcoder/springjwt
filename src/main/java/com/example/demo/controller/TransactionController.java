package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.services.TransactionService;

@RestController
@RequestMapping("group/{groupId}/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("all")
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions(@PathVariable Long groupId) {
        try {
            return new ResponseEntity<>(transactionService.getTransactionsByGroupId(groupId), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error getting transactions for group: " + groupId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("add")
    public ResponseEntity<TransactionRequestDTO> addTransaction(
            @PathVariable Long groupId, @RequestBody TransactionRequestDTO transactionDTO,
            Authentication authentication
        ) {
        try {

            transactionService.addTransactions(groupId, transactionDTO, authentication);
            return new ResponseEntity<>(transactionDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Adding transaction: " + transactionDTO);
            System.out.println("Error adding transaction: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("{transactionId}/delete")
    public ResponseEntity<String> deleteTransaction(
            @PathVariable Long groupId, @PathVariable Long transactionId) {
        try {
            transactionService.removeTransaction(transactionId);
            return ResponseEntity.ok("Transaction deleted successfully");
        } catch (Exception e) {
            System.out.println("Error deleting transaction: " + e.getMessage());
            return new ResponseEntity<>("Failed to delete transaction", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("{transactionId}/update")
    public ResponseEntity<TransactionRequestDTO> updateTransaction(
            @PathVariable Long groupId, @PathVariable Long transactionId,
            @RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            transactionService.updateTransaction(groupId, transactionId, transactionRequestDTO);
            return new ResponseEntity<>(transactionRequestDTO, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error updating transaction: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
