package com.example.demo.dto;

public class TransactionRequestDTO {
    private Long payeeId;
    private Long amount;

    public TransactionRequestDTO(Long payeeId, Long amount) {
        this.payeeId = payeeId;
        this.amount = amount;
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
