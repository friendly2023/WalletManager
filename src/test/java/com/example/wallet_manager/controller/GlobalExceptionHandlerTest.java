package com.example.wallet_manager.controller;

import com.example.wallet_manager.dto.ValidationErrorResponse;
import com.example.wallet_manager.dto.Violation;
import com.example.wallet_manager.exception.InsufficientFundsException;
import com.example.wallet_manager.exception.WalletNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleWalletNotFound() {
        UUID walletId = UUID.randomUUID();
        WalletNotFoundException ex = new WalletNotFoundException(walletId);

        ValidationErrorResponse response = handler.handleWalletNotFound(ex);

        assertNotNull(response);
        assertEquals(1, response.getViolations().size());

        Violation violation = response.getViolations().get(0);
        assertEquals("walletId", violation.getFieldName());
        assertEquals("Кошелек с идентификатором '" + walletId + "' не найден.", violation.getMessage());
    }

    @Test
    void testInsufficientFundsException(){
        UUID walletId = UUID.randomUUID();
        InsufficientFundsException ex = new InsufficientFundsException(walletId);

        ValidationErrorResponse response = handler.throwInsufficientFunds(ex);

        assertNotNull(response);
        assertEquals(1, response.getViolations().size());

        Violation violation = response.getViolations().get(0);
        assertEquals(null, violation.getFieldName());
        assertEquals("Недостаточно средств на кошельке '" + walletId + "' для списания.", violation.getMessage());
    }
}




