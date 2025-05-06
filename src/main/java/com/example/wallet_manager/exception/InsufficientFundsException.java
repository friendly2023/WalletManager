package com.example.wallet_manager.exception;

import java.util.UUID;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(UUID walletId) {
        super("Недостаточно средств на кошельке '" + walletId + "' для списания.");
    }
}
