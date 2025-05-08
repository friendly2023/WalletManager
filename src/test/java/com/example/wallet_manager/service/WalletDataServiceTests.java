package com.example.wallet_manager.service;

import com.example.wallet_manager.entity.Wallet;
import com.example.wallet_manager.exception.InvalidUUIDFormatException;
import com.example.wallet_manager.exception.WalletNotFoundException;
import com.example.wallet_manager.repository.WalletRepository;
import com.example.wallet_manager.service.verification.UUIDValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletDataServiceTests {

    @Mock
    private WalletRepository walletRepository;
    @Mock
    private UUIDValidator uuidValidator;

    @InjectMocks
    private WalletDataService walletDataService;

    @Test
    void getWalletByUUID_shouldReturnWallet_whenWalletExists() throws Exception {

        UUID walletId = UUID.randomUUID();
        Wallet expectedWallet = new Wallet();

        Field field = Wallet.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(expectedWallet, walletId);

        when(walletRepository.getWalletByUUID(walletId))
                .thenReturn(expectedWallet);

        Wallet actualWallet = walletDataService.getWalletByUUID(walletId);

        assertEquals(expectedWallet, actualWallet);
    }

    @Test
    void getWalletByUUID_shouldThrowException_whenWalletNotExists() {

        UUID walletId = UUID.randomUUID();

        when(walletRepository.getWalletByUUID(walletId))
                .thenThrow(new WalletNotFoundException(walletId));

        assertThrows(WalletNotFoundException.class, () -> {
            walletDataService.getWalletByUUID(walletId);
        });
    }

    @Test
    void getWalletByStringUUID_shouldReturnWallet_validationIsSuccessful() throws Exception {

        UUID walletId = UUID.randomUUID();
        String validUUIDString = walletId.toString();
        Wallet expectedWallet = new Wallet();

        Field field = Wallet.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(expectedWallet, walletId);

        when(walletRepository.getWalletByUUID(walletId))
                .thenReturn(expectedWallet);

        Wallet actualWallet = walletDataService.getWalletByStringUUID(validUUIDString);

        verify(uuidValidator).validate(validUUIDString);
        assertEquals(expectedWallet, actualWallet);
    }

    @Test
    void getWalletByStringUUID_shouldThrowException_validationIsNotSuccessful() {

        String invalidUUID = "invalid-uuid";

        doThrow(new InvalidUUIDFormatException())
                .when(uuidValidator).validate(invalidUUID);

        assertThrows(InvalidUUIDFormatException.class,
                () -> walletDataService.getWalletByStringUUID(invalidUUID));

        verify(uuidValidator).validate(invalidUUID);
        verifyNoInteractions(walletRepository);
    }
}
