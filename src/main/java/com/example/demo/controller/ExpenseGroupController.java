package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GroupMemberResponseDTO;
import com.example.demo.model.ExpenseGroup;
import com.example.demo.repo.ExpenseGroupRepo;

import com.example.demo.services.ExpenseGroupService;

@RestController
@RequestMapping("group")
public class ExpenseGroupController {

    @Autowired
    ExpenseGroupRepo groupRepo;
    @Autowired
    ExpenseGroupService groupService;

    @PostMapping("create")
    public ResponseEntity<ExpenseGroup> createGroup(@RequestParam String groupName, Authentication authentication) {
        if (!groupRepo.existsByGroupName(groupName)) {
            ExpenseGroup group = groupService.createGroup(groupName, authentication.getName());
            return new ResponseEntity<>(group, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{id}/addMember")
    public ResponseEntity<String> addUser(@PathVariable Long id, @RequestParam String username) {
        try {
            groupService.addMember(id, username);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}/members")
    public ResponseEntity<List<GroupMemberResponseDTO>> getMembers(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(groupService.getMembers(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ExpenseGroup> getGroup(@PathVariable Long id) {
        return groupRepo.findById(id)
                .map(group -> new ResponseEntity<>(group, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}