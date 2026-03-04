package com.example.demo.utils;

import com.example.demo.dto.CostShareDTO;
import com.example.demo.model.MemberCostShare;

public class CostShareMapper {
    public static CostShareDTO toCostShareDTO(MemberCostShare member) {
        CostShareDTO costShareDTO=new CostShareDTO();
        costShareDTO.setDept(member.getDept());
        costShareDTO.setGroupId(member.getGroup().getGroupId());
        costShareDTO.setMemberId(member.getGroupMember().getGroupMemberId());
        costShareDTO.setMemberName(member.getGroupMember().getUser().getUsername());
        costShareDTO.setTransactionId(member.getTransaction().getTransactionId());
        return costShareDTO;
    }
}
