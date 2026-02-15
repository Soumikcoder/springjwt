package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ExpenseGroup;
import com.example.demo.model.GroupMember;
import com.example.demo.model.MyUserDetails;
import com.example.demo.repo.ExpenseGroupRepo;
import com.example.demo.repo.UserRepo;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

;

@RestController
@RequestMapping("group")
public class ExpenseGroupController {
    @Autowired
    ExpenseGroupRepo groupRepo;
    @Autowired
    UserRepo userRepo;

    @PostMapping("create")
    public ResponseEntity<ExpenseGroup> createGroup(@RequestBody String groupName, Authentication authentication) {
        ExpenseGroup group = new ExpenseGroup();
        group.setGroupName(groupName);
        List<GroupMember> members = group.getUsers();
        MyUserDetails details = userRepo.findByUsername(authentication.getName()).get();
        members.add(new GroupMember(group, details));
        groupRepo.save(group);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

}
