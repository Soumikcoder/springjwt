package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CostShareDTO;
import com.example.demo.model.MemberCostShare;
import com.example.demo.services.MemberCostShareService;

@RestController
@RequestMapping("transaction/{transactionId}/costshare")
public class CostShareController {
    @Autowired
    MemberCostShareService memberCostShareService;
    @GetMapping("all")
    public List<CostShareDTO> getMemberCostSharesByGroupId(Long transactionId) {
        return memberCostShareService.getMemberCostSharesByTransactionId(transactionId);
    }
}
