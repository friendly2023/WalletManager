package com.example.wallet_manager.service;

import com.example.wallet_manager.dto.WalletOperationRequest;
import com.example.wallet_manager.entity.Wallet;
import com.example.wallet_manager.exception.InsufficientFundsException;
import com.example.wallet_manager.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.UUID;

import static com.example.wallet_manager.enums.OperationType.DEPOSIT;
import static com.example.wallet_manager.enums.OperationType.WITHDRAW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WalletOperationServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletDataService walletDataService;

    @InjectMocks
    private WalletOperationService walletOperationService;

    @Test
    void applyOperation_successfulReplenishmentOfWallet() throws Exception {

        WalletOperationRequest walletOperationRequest = new WalletOperationRequest();
        walletOperationRequest.setOperationType(DEPOSIT);
        walletOperationRequest.setAmount(BigDecimal.valueOf(1000));
        UUID walletId = UUID.randomUUID();

        Field field = WalletOperationRequest.class.getDeclaredField("walletId");
        field.setAccessible(true);
        field.set(walletOperationRequest, walletId);

        Wallet expectedWallet = new Wallet();
        expectedWallet.setBalance(BigDecimal.valueOf(1000));

        Field field1 = Wallet.class.getDeclaredField("id");
        field1.setAccessible(true);
        field1.set(expectedWallet, walletId);

        when(walletDataService.getWalletByUUID(walletId))
                .thenReturn(expectedWallet);

        Wallet response = walletOperationService.applyOperation(walletOperationRequest);

        verify(walletRepository, times(1)).save(expectedWallet);

        assertEquals(BigDecimal.valueOf(2000), response.getBalance());
    }

    @Test
    void applyOperation_successfulDebitFromWallet() throws Exception {

        WalletOperationRequest walletOperationRequest = new WalletOperationRequest();
        walletOperationRequest.setOperationType(WITHDRAW);
        walletOperationRequest.setAmount(BigDecimal.valueOf(100));
        UUID walletId = UUID.randomUUID();

        Field field = WalletOperationRequest.class.getDeclaredField("walletId");
        field.setAccessible(true);
        field.set(walletOperationRequest, walletId);

        Wallet expectedWallet = new Wallet();
        expectedWallet.setBalance(BigDecimal.valueOf(1000));

        Field field1 = Wallet.class.getDeclaredField("id");
        field1.setAccessible(true);
        field1.set(expectedWallet, walletId);

        when(walletDataService.getWalletByUUID(walletId))
                .thenReturn(expectedWallet);

        Wallet response = walletOperationService.applyOperation(walletOperationRequest);

        verify(walletRepository, times(1)).save(expectedWallet);

        assertEquals(BigDecimal.valueOf(900), response.getBalance());
    }

    @Test
    void applyOperation_notSuccessfully() throws Exception {

        WalletOperationRequest walletOperationRequest = new WalletOperationRequest();
        walletOperationRequest.setOperationType(WITHDRAW);
        walletOperationRequest.setAmount(BigDecimal.valueOf(1000));
        UUID walletId = UUID.randomUUID();

        Field field = WalletOperationRequest.class.getDeclaredField("walletId");
        field.setAccessible(true);
        field.set(walletOperationRequest, walletId);

        Wallet expectedWallet = new Wallet();
        expectedWallet.setBalance(BigDecimal.valueOf(100));

        Field field1 = Wallet.class.getDeclaredField("id");
        field1.setAccessible(true);
        field1.set(expectedWallet, walletId);

        when(walletDataService.getWalletByUUID(walletId))
                .thenReturn(expectedWallet);

        assertThrows(InsufficientFundsException.class, () ->
                walletOperationService.applyOperation(walletOperationRequest)
        );

        verify(walletRepository, never()).save(any());
    }
}
