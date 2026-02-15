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
    MyUserDetails paidBy;
    @ManyToOne
    ExpenseGroup group;
    Long amount;
    @OneToMany
    List<MemberCostShare> shares;

    public Transactions() {
    }

    public Transactions(Long transactionId, MyUserDetails paidBy, ExpenseGroup group, Long amount,
            List<MemberCostShare> shares) {
        this.transactionId = transactionId;
        this.paidBy = paidBy;
        this.group = group;
        this.amount = amount;
        this.shares = shares;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public MyUserDetails getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(MyUserDetails paidBy) {
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

    public List<MemberCostShare> getShares() {
        return shares;
    }

    public void setShares(List<MemberCostShare> shares) {
        this.shares = shares;
    }

}
