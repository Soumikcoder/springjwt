package com.example.demo.dto;

public class TransactionRequestDTO {
    private Long payeeId;
    private Long amount;
    private String payeeName;
    private String groupName;

    public TransactionRequestDTO() {
    }

    public TransactionRequestDTO(Long payeeId, Long amount) {
        this.payeeId = payeeId;
        this.amount = amount;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "payeeId=" + payeeId +
                ", amount=" + amount +
                '}';
    }
}
