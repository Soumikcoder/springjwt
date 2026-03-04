package com.example.demo.utils;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.model.Transactions;


public class TransactionMapper {
    public static TransactionDTO toTransactionDTO(Transactions transaction) {
        TransactionDTO transactionDTO=new TransactionDTO();
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setPayeeId(transaction.getPaidBy().getGroupMemberId());
        transactionDTO.setGroupName(transaction.getGroup().getGroupName());
        transactionDTO.setPayeeName(transaction.getPaidBy().getUser().getUsername());
        return transactionDTO;
    }
}
