package com.example.demo.dto;

public class CostShareDTO {
    private Long memberId;
    private Long dept;
    private String memberName;
    private Long groupId;
    public Long transactionId;
    public CostShareDTO() {
    }
    
    

    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public Long getDept() {
        return dept;
    }
    public void setDept(Long dept) {
        this.dept = dept;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public Long getGroupId() {
        return groupId;
    }
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }



    public Long getTransactionId() {
        return transactionId;
    }



    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
    
}
