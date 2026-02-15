package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ExpenseGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long groupId;
    String groupName;

    @OneToMany
    List<GroupMember> users;

    public ExpenseGroup() {
    }

    public ExpenseGroup(Long groupId, String groupName, List<GroupMember> users) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.users = users;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<GroupMember> getUsers() {
        return users;
    }

    public void setUsers(List<GroupMember> users) {
        this.users = users;
    }

}
