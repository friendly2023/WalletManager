package com.example.wallet_manager.dto;

import com.example.wallet_manager.enums.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletOperationRequest {

    private UUID id;
    private OperationType operationType;
    private BigDecimal amount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
