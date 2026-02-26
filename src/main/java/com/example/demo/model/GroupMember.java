package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long groupMemberId;

    @ManyToOne
    ExpenseGroup group;

    @ManyToOne
    MyUserDetails user;
    Long balance;

    public GroupMember() {
    }

    public GroupMember(ExpenseGroup group, MyUserDetails user) {
        this.group = group;
        this.user = user;
        balance = 0L;
    }

    public GroupMember(Long groupMemberId, ExpenseGroup group, MyUserDetails user, Long balance) {
        this.groupMemberId = groupMemberId;
        this.group = group;
        this.user = user;
        this.balance = balance;
    }

    public Long getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(Long groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public ExpenseGroup getGroup() {
        return group;
    }

    public void setGroup(ExpenseGroup group) {
        this.group = group;
    }

    public MyUserDetails getUser() {
        return user;
    }

    public void setUser(MyUserDetails user) {
        this.user = user;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

}
