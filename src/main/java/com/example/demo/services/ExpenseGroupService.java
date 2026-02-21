package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ExpenseGroup;
import com.example.demo.model.GroupMember;
import com.example.demo.model.MyUserDetails;
import com.example.demo.repo.ExpenseGroupRepo;
import com.example.demo.repo.UserRepo;

@Service
public class ExpenseGroupService {
    @Autowired
    ExpenseGroupRepo groupRepo;
    @Autowired
    UserRepo userRepo;

    public ExpenseGroup createGroup(String groupName, String username) {
        ExpenseGroup group = new ExpenseGroup();
        group.setGroupName(groupName);
        List<GroupMember> members = new ArrayList<>();
        MyUserDetails details = userRepo.findByUsername(username).get();
        members.add(new GroupMember(group, details));
        group.setUsers(members);
        groupRepo.save(group);
        return group;
    }

    public void addMember(Long id, String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMember'");
    }
}
