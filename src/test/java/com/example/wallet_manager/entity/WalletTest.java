package com.example.wallet_manager.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletTest {

    @Test
    void workingWithSmallAmounts() {
        Wallet wallet1 = new Wallet();
        wallet1.setBalance(BigDecimal.valueOf(0.01));

        Wallet wallet2 = new Wallet();
        wallet2.setBalance(BigDecimal.valueOf(0.02));

        BigDecimal amount = wallet1.getBalance().add(wallet2.getBalance());
        BigDecimal difference = wallet2.getBalance().subtract(wallet1.getBalance());
        BigDecimal complexCalculation = BigDecimal.valueOf(9.99).add(BigDecimal.valueOf(7.77))
                                                                .subtract(BigDecimal.valueOf(12.12));

        assertEquals(BigDecimal.valueOf(0.03), amount);
        assertEquals(BigDecimal.valueOf(0.01), difference);
        assertEquals(BigDecimal.valueOf(5.64), complexCalculation);
    }
}
