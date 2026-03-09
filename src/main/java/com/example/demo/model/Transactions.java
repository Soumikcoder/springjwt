package com.example.demo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long transactionId;
    @ManyToOne(cascade = CascadeType.ALL)
    GroupMember paidBy;
    @ManyToOne(cascade = CascadeType.ALL)
    ExpenseGroup group;
    Long amount;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime payedAt;

    public Transactions() {
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Transactions(Long transactionId, GroupMember paidBy, ExpenseGroup group, Long amount,
            LocalDateTime payedAt) {
        this.transactionId = transactionId;
        this.paidBy = paidBy;
        this.group = group;
        this.amount = amount;
        this.payedAt = payedAt;
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

    public LocalDateTime getPayedAt() {
        return payedAt;
    }

    public void setPayedAt(LocalDateTime payedAt) {
        this.payedAt = payedAt;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

}
