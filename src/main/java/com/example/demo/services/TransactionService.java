package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.model.ExpenseGroup;
import com.example.demo.model.Transactions;
import com.example.demo.repo.TransactionsRepo;
import com.example.demo.utils.TransactionMapper;

@Service
public class TransactionService {

    @Autowired
    ExpenseGroupService expenseGroupService;
    @Autowired
    TransactionsRepo transactionRepo;
    @Autowired
    GroupMemberService groupMemberService;
    @Autowired
    MemberCostShareService memberCostShareService;

    public void addTransactions(Long groupId, TransactionRequestDTO transactionDTO
        ,Authentication authentication) throws Exception {
        if (!expenseGroupService.existsByGroupID(groupId)) {
            throw new IllegalArgumentException("Group does not exist");
        }
        ExpenseGroup group = expenseGroupService.getGroupByID(groupId).orElseThrow();
        Long payeeId = transactionDTO.getPayeeId();
        if (!groupMemberService.isSamePayeeAndLoggedUser(payeeId,authentication)) {
            throw new IllegalArgumentException("Payee does not match the authenticated user" +
                    "\n Payee id: " + payeeId + "\n Authenticated user id: " + authentication.getName());
        }
        if (!groupMemberService.existsByGroupIdAndMemberId(groupId, payeeId)) {
            throw new IllegalArgumentException("Payee is not a member of the group" +
                    "\n Payee id: " + payeeId + "\n Group id: " + groupId);
        }
        Long amount = transactionDTO.getAmount();
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeTransaction'");
    }

    public void updateTransaction(Long groupId, Long transactionId, TransactionRequestDTO transactionDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTransaction'");
    }

    public boolean existsByTransactionId(Long transactionId) {
        // TODO Auto-generated method stub
        return transactionRepo.existsById(transactionId);
    }

}
