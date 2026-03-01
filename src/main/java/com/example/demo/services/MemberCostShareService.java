package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ExpenseGroup;
import com.example.demo.model.GroupMember;
import com.example.demo.model.MemberCostShare;
import com.example.demo.model.Transactions;
import com.example.demo.repo.MemberCostShareRepo;

import jakarta.transaction.Transaction;

@Service
public class MemberCostShareService {
    @Autowired
    MemberCostShareRepo memberCostShareRepo;
    @Autowired
    GroupMemberService groupMemberService;

    public void addCostShareToMember(Transactions transaction) {
        Long groupId = transaction.getGroup().getGroupId();
        GroupMember paidBy = transaction.getPaidBy();
        Long amount = transaction.getAmount();
        List<GroupMember> groupMembers = groupMemberService.getGroupMembersByGroupId(groupId);
        Long share = amount / groupMembers.size();
        paidBy.setBalance(amount - share);
        groupMemberService.addBalanaceToMember(groupId, paidBy.getGroupMemberId(), amount - share);
        List<MemberCostShare> memberCostShares = new ArrayList<>();
        groupMembers.forEach(member -> {
            MemberCostShare memberCostShare = new MemberCostShare();
            memberCostShare.setGroupMember(paidBy);
            memberCostShare.setTransaction(transaction);
            memberCostShares.add(memberCostShare);
            if (member.getGroupMemberId().equals(paidBy.getGroupMemberId())) {
                memberCostShare.setDept(amount - share);
                return;
            }
            memberCostShare.setDept(-share);
            groupMemberService.addBalanaceToMember(groupId, member.getGroupMemberId(), -share);

        });

        memberCostShares.forEach(memberCostShare -> {
            memberCostShareRepo.save(memberCostShare);
        });

    }

    // TODO: write controller for this service and test it
    public List<MemberCostShare> getMemberCostSharesByGroupId(Long groupId) {
        return memberCostShareRepo.findByGroupGroupId(groupId);
    }

    public List<MemberCostShare> getMemberCostSharesByTransactionId(Long transactionId) {
        return memberCostShareRepo.findByTransactionTransactionId(transactionId);
    }
}
