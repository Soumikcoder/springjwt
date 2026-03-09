package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.AppException;
import com.example.demo.dto.GroupMemberResponseDTO;
import com.example.demo.model.ExpenseGroup;
import com.example.demo.model.GroupMember;
import com.example.demo.model.MyUserDetails;
import com.example.demo.repo.ExpenseGroupRepo;
import com.example.demo.repo.GroupMemberRepo;
import com.example.demo.utils.GroupMemberResonseMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExpenseGroupService {
    ExpenseGroupRepo groupRepo;
    GroupMemberRepo groupMemberRepo;
    UserService userService;

    public ExpenseGroup createGroup(String groupName, String username) {
        if (groupName==null || username == null) {
            throw new AppException("Groupname & Username can not be null!",
            HttpStatus.BAD_REQUEST);
        }
        ExpenseGroup group = new ExpenseGroup();
        group.setGroupName(groupName);

        MyUserDetails details = userService.loadUserByUsername(username);
        GroupMember member = new GroupMember(group, details);
        groupRepo.save(group);
        groupMemberRepo.save(member);
        return group;
    }

    public void addMember(Long id, String username) {
        if (userService.isExistsUsername(username)
                &&
                groupRepo.existsById(id)) {
            ExpenseGroup group = groupRepo.findById(id).orElseThrow();
            MyUserDetails details = userService.loadUserByUsername(username);
            GroupMember member = new GroupMember(group, details);
            groupMemberRepo.save(member);
        }
       throw new AppException(
        String.format("Group ID %d or Username %s does not exist!",id, username),
        HttpStatus.BAD_REQUEST);
    }

    public List<GroupMemberResponseDTO> getMembers(Long id) {
        if (groupRepo.existsById(id)) {
            return groupMemberRepo.findByGroupGroupId(id).stream()
            .map(GroupMemberResonseMapper::tGroupMemberResponseDTO)
            .toList();
        }
        throw new AppException("Group not found", HttpStatus.NOT_FOUND);
    }

    public boolean existsByGroupName(String groupName) {
        return groupRepo.existsByGroupName(groupName);
    }

    public boolean existsByGroupID(Long id) {
        return groupRepo.existsById(id);
    }

    public Optional<ExpenseGroup> getGroupByID(Long id) {
        return groupRepo.findById(id);
    }

    public void deleteGroup(Long id) {
        if (groupRepo.existsById(id)) {
            groupRepo.deleteById(id);   
        }
        else {
            throw new AppException("Group with id: " + id + " does not exist", 
            HttpStatus.NOT_FOUND);
        }
    }
}
