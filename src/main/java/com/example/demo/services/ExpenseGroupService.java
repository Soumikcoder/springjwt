package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ExpenseGroup;
import com.example.demo.model.GroupMember;
import com.example.demo.model.MyUserDetails;
import com.example.demo.repo.ExpenseGroupRepo;
import com.example.demo.repo.GroupMemberRepo;
import com.example.demo.repo.UserRepo;

@Service
public class ExpenseGroupService {
    @Autowired
    ExpenseGroupRepo groupRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
     GroupMemberRepo groupMemberRepo;

    public ExpenseGroup createGroup(String groupName, String username) {
        ExpenseGroup group = new ExpenseGroup();
        group.setGroupName(groupName);
        
        MyUserDetails details = userRepo.findByUsername(username).get();
        GroupMember member = new GroupMember(group,details);
        groupRepo.save(group);
        groupMemberRepo.save(member);
        return group;
    }

    public void addMember(Long id, String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMember'");
    }
}
