package com.example.demo.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.AppException;
import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.model.ExpenseGroup;
import com.example.demo.model.Transactions;
import com.example.demo.repo.TransactionsRepo;
import com.example.demo.utils.TransactionMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {

    ExpenseGroupService expenseGroupService;
    TransactionsRepo transactionRepo;
    GroupMemberService groupMemberService;
    MemberCostShareService memberCostShareService;

    public void addTransactions(Long groupId, TransactionRequestDTO transactionDTO
        ,Authentication authentication) {
        if (!expenseGroupService.existsByGroupID(groupId)) {
            throw new AppException(
                String.format("Group %d does not exist", groupId),
                HttpStatus.BAD_REQUEST
            );
        }
        ExpenseGroup group = expenseGroupService.getGroupByID(groupId).orElseThrow();
        Long payeeId = transactionDTO.getPayeeId();
        if (!groupMemberService.isSamePayeeAndLoggedUser(payeeId,authentication)) {
            throw new AppException("Payee does not match the authenticated user" +
                    "\n Payee id: " + payeeId + "\n Authenticated user id: " + authentication.getName(),
                    HttpStatus.BAD_REQUEST);
        }
        if (!groupMemberService.existsByGroupIdAndMemberId(groupId, payeeId)) {
            throw new AppException("Payee is not a member of the group" +
                    "\n Payee id: " + payeeId + "\n Group id: " + groupId,
                    HttpStatus.BAD_REQUEST);
        }
        Long amount = transactionDTO.getAmount();
        if (amount <= 0) {
            throw new AppException("Amount must be greater than zero",
                    HttpStatus.BAD_REQUEST);
        }
        Transactions transaction = new Transactions();
        transaction.setGroup(group);
        transaction.setAmount(amount);
        transaction.setPaidBy(groupMemberService.getGroupMemberById(payeeId));
        transactionRepo.save(transaction);
        memberCostShareService.addCostShareToMember(transaction);
    }

    public List<TransactionResponseDTO> getTransactionsByGroupId(Long groupId) {
        return transactionRepo.findByGroupGroupId(groupId).stream()
                .map(TransactionMapper::toTransactionResponseDTO)  
                .toList();
    }

    public void removeTransaction(Long transactionId) {
        if (!existsByTransactionId(transactionId)) {
            throw new AppException(
                String.format("Transaction with id %d does not exist", transactionId),
                HttpStatus.BAD_REQUEST
            );
        }
        transactionRepo.deleteById(transactionId);
    }

    public void updateTransaction(Long groupId, Long transactionId, TransactionRequestDTO transactionDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTransaction'");
    }

    public boolean existsByTransactionId(Long transactionId) {
        return transactionRepo.existsById(transactionId);
    }

}
