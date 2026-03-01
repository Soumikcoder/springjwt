package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.GroupMember;
import com.example.demo.repo.GroupMemberRepo;

@Service
public class GroupMemberService {
    @Autowired
    GroupMemberRepo groupMemberRepo;

    public boolean existsByGroupMemberId(Long groupMemberId) {
        return groupMemberRepo.existsById(groupMemberId);
    }

    public boolean existsByGroupIdAndMemberId(Long groupId, Long memberId) {
        return groupMemberRepo.existsByGroupGroupIdAndGroupMemberId(groupId, memberId);
    }

    public void addBalanaceToMember(Long groupId, Long memberId, Long amount) {
        if (existsByGroupIdAndMemberId(groupId, memberId)) {
            groupMemberRepo.findById(memberId).ifPresent(
                    groupMember -> groupMember.setBalance(groupMember.getBalance() + amount));
        }
    }

    public GroupMember getGroupMemberById(Long groupMemberId) {
        return groupMemberRepo.findById(groupMemberId).orElse(null);
    }

    public List<GroupMember> getGroupMembersByGroupId(Long groupId) {
        // TODO Auto-generated method stub
        return groupMemberRepo.findByGroupGroupId(groupId);
    }
}
