package com.example.demo.utils;

import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.model.Transactions;


public class TransactionMapper {
    public static TransactionResponseDTO toTransactionResponseDTO(Transactions transaction) {
        TransactionResponseDTO transactionDTO=new TransactionResponseDTO();
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setPayeeId(transaction.getPaidBy().getGroupMemberId());
        transactionDTO.setTransactionId(transaction.getTransactionId());
        transactionDTO.setPayeeName(transaction.getPaidBy().getUser().getUsername());
        return transactionDTO;
    }
}
