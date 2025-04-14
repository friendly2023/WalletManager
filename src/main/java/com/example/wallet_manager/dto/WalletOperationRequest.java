package com.example.wallet_manager.dto;

import com.example.wallet_manager.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletOperationRequest {

    private UUID id;
    private OperationType operationType;
    private BigDecimal amount;

    @JsonCreator
    public WalletOperationRequest(
            @JsonProperty(value = "id", required = true) @NotNull UUID id,
            @JsonProperty(value = "operationType", required = true) @NotNull OperationType operationType,
            @JsonProperty(value = "amount", required = true) @DecimalMin("0.01") BigDecimal amount
    ) {
        this.id = id;
        this.operationType = operationType;
        this.amount = amount;
    }

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
