package com.example.demo.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.GroupMember;
import com.example.demo.repo.GroupMemberRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GroupMemberService {
    GroupMemberRepo groupMemberRepo;

    public boolean existsByGroupMemberId(Long groupMemberId) {
        return groupMemberRepo.existsById(groupMemberId);
    }

    public boolean existsByGroupIdAndMemberId(Long groupId, Long memberId) {
        return groupMemberRepo.existsByGroupGroupIdAndGroupMemberId(groupId, memberId);
    }
    @Transactional
    public void addBalanaceToMember(Long groupId, Long memberId, Long amount) {
        if (existsByGroupIdAndMemberId(groupId, memberId)) {
            System.out.println("Adding balance to member with id: " + memberId + " in group with id: " + groupId + " amount: " + amount);
            groupMemberRepo.findById(memberId).ifPresent(
                    groupMember -> groupMember.setBalance(groupMember.getBalance() + amount));
        }
    }

    public GroupMember getGroupMemberById(Long groupMemberId) {
        return groupMemberRepo.findById(groupMemberId).orElse(null);
    }

    public List<GroupMember> getGroupMembersByGroupId(Long groupId) {
        return groupMemberRepo.findByGroupGroupId(groupId);
    }

    public boolean isSamePayeeAndLoggedUser(Long payeeId, Authentication authentication) {
        return groupMemberRepo
        .findById(payeeId).get()
        .getUser().getUsername()
        .equals(authentication.getName());
    }
}
