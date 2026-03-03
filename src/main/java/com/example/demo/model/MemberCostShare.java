package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class MemberCostShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long memberCostShareId;
    @ManyToOne(cascade = CascadeType.ALL)
    GroupMember groupMember;
    @ManyToOne(cascade = CascadeType.ALL)
    ExpenseGroup group;
    @ManyToOne(cascade = CascadeType.ALL)
    Transactions transaction;
    Long dept;

    public MemberCostShare() {
    }

    public MemberCostShare(Long memberCostShareId, GroupMember groupMember, ExpenseGroup group,
            Transactions transaction,
            Long dept) {
        this.memberCostShareId = memberCostShareId;
        this.groupMember = groupMember;
        this.group = group;
        this.transaction = transaction;
        this.dept = dept;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public void setTransaction(Transactions transaction) {
        this.transaction = transaction;
    }

    public Long getMemberCostShareId() {
        return memberCostShareId;
    }

    public void setMemberCostShareId(Long memberCostShareId) {
        this.memberCostShareId = memberCostShareId;
    }

    public GroupMember getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(GroupMember groupMember) {
        this.groupMember = groupMember;
    }

    public ExpenseGroup getGroup() {
        return group;
    }

    public void setGroup(ExpenseGroup group) {
        this.group = group;
    }

    public Long getDept() {
        return dept;
    }

    public void setDept(Long dept) {
        this.dept = dept;
    }

}
