package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ExpenseGroup;
import com.example.demo.model.GroupMember;
import com.example.demo.repo.GroupMemberRepo;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("group/member")
public class GroupMemberController {
    @Autowired
    GroupMemberRepo groupMemberRepo;
    @Autowired
    UserService userService;
    @GetMapping("all")
    public ResponseEntity<List<ExpenseGroup>> getGroupByUser(Authentication authentication) {
        List<GroupMember> groupMembers = groupMemberRepo.findByUserUsername(
            authentication.getName()
        );
        List<ExpenseGroup> groups = groupMembers.stream().map(GroupMember::getGroup).toList();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }
}

