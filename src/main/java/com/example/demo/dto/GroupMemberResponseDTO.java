package com.example.demo.dto;

public class GroupMemberResponseDTO {
    private Long groupMemberId;
    private String username;
    private Long balance;

    public GroupMemberResponseDTO() {
    }

    public GroupMemberResponseDTO(Long groupMemberId, String username, Long balance) {
        this.groupMemberId = groupMemberId;
        this.username = username;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(Long groupMemberId) {
        this.groupMemberId = groupMemberId;
    }
}
