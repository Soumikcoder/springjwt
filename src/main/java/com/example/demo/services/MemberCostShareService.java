package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CostShareDTO;
import com.example.demo.model.ExpenseGroup;
import com.example.demo.model.GroupMember;
import com.example.demo.model.MemberCostShare;
import com.example.demo.model.Transactions;
import com.example.demo.repo.MemberCostShareRepo;
import com.example.demo.utils.CostShareMapper;


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
        paidBy.setBalance(paidBy.getBalance() + amount - share);
        List<MemberCostShare> memberCostShares = new ArrayList<>();
        ExpenseGroup group = transaction.getGroup();
        System.out.println(groupMembers);
        groupMembers.forEach(member -> {
            MemberCostShare memberCostShare = new MemberCostShare();
            memberCostShare.setGroupMember(member);
            memberCostShare.setTransaction(transaction);
            memberCostShares.add(memberCostShare);
            memberCostShare.setGroup(group);
            if (member.getGroupMemberId().equals(paidBy.getGroupMemberId())) {
                memberCostShare.setDept(amount - share);
                return;
            }
            memberCostShare.setDept(-share);
            member.setBalance(member.getBalance() - share);

        });

        memberCostShares.forEach(memberCostShare -> {
            memberCostShareRepo.save(memberCostShare);
        });

    }

    

    public List<CostShareDTO> getMemberCostSharesByTransactionId(Long transactionId) {
        return memberCostShareRepo.findByTransactionTransactionId(transactionId).stream()
        .map(CostShareMapper::toCostShareDTO).toList();
    }

}
