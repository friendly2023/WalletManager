package com.example.wallet_manager.dto;

import com.example.wallet_manager.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletOperationRequest {

    @NotNull(groups = {WalletData.class})
    @JsonView({WalletData.class})
    private UUID walletId;

    @NotNull(groups = {WalletData.class})
    @JsonView({WalletData.class})
    private OperationType operationType;

    @NotNull(groups = {WalletData.class})
    @JsonView({WalletData.class})
    @DecimalMin(value = "0.01", groups = {WalletData.class})
    private BigDecimal amount;

    public WalletOperationRequest() {
    }

    public UUID getWalletId() {
        return walletId;
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

    public interface WalletData {
    }
}
