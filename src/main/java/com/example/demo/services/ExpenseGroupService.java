package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ExpenseGroup;
import com.example.demo.model.GroupMember;
import com.example.demo.model.MyUserDetails;
import com.example.demo.repo.ExpenseGroupRepo;
import com.example.demo.repo.GroupMemberRepo;

@Service
public class ExpenseGroupService {
    @Autowired
    ExpenseGroupRepo groupRepo;
    @Autowired
    GroupMemberRepo groupMemberRepo;
    @Autowired
    UserService userService;

    public ExpenseGroup createGroup(String groupName, String username) {
        ExpenseGroup group = new ExpenseGroup();
        group.setGroupName(groupName);
        
        MyUserDetails details = userService.loadUserByUsername(username);
        GroupMember member = new GroupMember(group,details);
        groupRepo.save(group);
        groupMemberRepo.save(member);
        return group;
    }

    public boolean addMember(Long id, String username) {
        if (userService.isExistsUsername(username) 
            &&
            groupRepo.existsById(id)
        ) {
            ExpenseGroup group = groupRepo.findById(id).orElseThrow();
            MyUserDetails details = userService.loadUserByUsername(username);
            GroupMember member = new GroupMember(group,details);
            groupMemberRepo.save(member);
            return true;
        }
        return false;
    }
}
