package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long transactionId;
    @ManyToOne
    GroupMember paidBy;
    @ManyToOne
    ExpenseGroup group;
    Long amount;

    public Transactions() {
    }

    public Transactions(Long transactionId, GroupMember paidBy, ExpenseGroup group, Long amount) {
        this.transactionId = transactionId;
        this.paidBy = paidBy;
        this.group = group;
        this.amount = amount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public GroupMember getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(GroupMember paidBy) {
        this.paidBy = paidBy;
    }

    public ExpenseGroup getGroup() {
        return group;
    }

    public void setGroup(ExpenseGroup group) {
        this.group = group;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

}
