package com.example.demo.utils;

import com.example.demo.dto.GroupMemberResponseDTO;
import com.example.demo.model.GroupMember;

public class GroupMemberResonseMapper {
    public static GroupMemberResponseDTO tGroupMemberResponseDTO(GroupMember groupMember) {
        GroupMemberResponseDTO groupMemberResponseDTO=new GroupMemberResponseDTO();
        groupMemberResponseDTO.setBalance(groupMember.getBalance());
        groupMemberResponseDTO.setGroupMemberId(groupMember.getGroupMemberId());
        groupMemberResponseDTO.setUsername(groupMember.getUser().getUsername());
        return groupMemberResponseDTO;
    }
}
