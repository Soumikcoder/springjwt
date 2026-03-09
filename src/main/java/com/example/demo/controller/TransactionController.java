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
        return new ResponseEntity<>(transactionService.getTransactionsByGroupId(groupId), HttpStatus.OK);
        
    }

    @PostMapping("add")
    public ResponseEntity<TransactionRequestDTO> addTransaction(
            @PathVariable Long groupId, @RequestBody TransactionRequestDTO transactionDTO,
            Authentication authentication
        ) {     
        transactionService.addTransactions(groupId, transactionDTO, authentication);
        return new ResponseEntity<>(transactionDTO, HttpStatus.CREATED);

    }

    @DeleteMapping("{transactionId}/delete")
    public ResponseEntity<String> deleteTransaction(
            @PathVariable Long groupId, @PathVariable Long transactionId) {
        transactionService.removeTransaction(transactionId);
        return ResponseEntity.ok("Transaction deleted successfully");
        
    }

    @PostMapping("{transactionId}/update")
    public ResponseEntity<TransactionRequestDTO> updateTransaction(
            @PathVariable Long groupId, @PathVariable Long transactionId,
            @RequestBody TransactionRequestDTO transactionRequestDTO) {
        
        transactionService.updateTransaction(groupId, transactionId, transactionRequestDTO);
        return new ResponseEntity<>(transactionRequestDTO, HttpStatus.OK);
        
    }
}
