package com.example.wallet_manager.dto;

import com.example.wallet_manager.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletOperationRequest {

    private UUID walletId;
    private OperationType operationType;
    private BigDecimal amount;

    @JsonCreator
    public WalletOperationRequest(
            @JsonProperty(value = "walletId", required = true) @NotNull UUID walletId,
            @JsonProperty(value = "operationType", required = true) @NotNull OperationType operationType,
            @JsonProperty(value = "amount", required = true) @DecimalMin("0.01") BigDecimal amount
    ) {
        this.walletId = walletId;
        this.operationType = operationType;
        this.amount = amount;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
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
