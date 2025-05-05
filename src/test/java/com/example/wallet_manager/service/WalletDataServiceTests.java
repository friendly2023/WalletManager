package com.example.wallet_manager.service;

import com.example.wallet_manager.dto.WalletOperationRequest;
import com.example.wallet_manager.entity.Wallet;
import com.example.wallet_manager.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WalletDataServiceTests {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletDataService walletDataService;

    @Test
    void getWalletByUUID_shouldReturnWallet_whenWalletExists() throws Exception {

        UUID walletId = UUID.randomUUID();
        Wallet expectedWallet = new Wallet();

        Field field = Wallet.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(expectedWallet, walletId);

        Mockito.when(walletRepository.findById(walletId))
                .thenReturn(Optional.of(expectedWallet));

        Wallet actualWallet = walletDataService.getWalletByUUID(walletId);

        assertEquals(expectedWallet, actualWallet);
    }
}


//@SpringBootTest
//public class WalletDataServiceTests {
//
//    @MockBean
//    private WalletRepository walletRepository;
//
//    @Autowired
//    private WalletDataService walletDataService;
//
//    @Test
//    void getWalletByUUID_shouldReturnWallet_whenWalletExists() throws Exception {
//        UUID walletId = UUID.randomUUID();
//        Wallet expectedWallet = new Wallet();
//
//        Field field = Wallet.class.getDeclaredField("id");
//        field.setAccessible(true);
//        field.set(expectedWallet, walletId);
//
//        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.of(expectedWallet));
//
//        Wallet actualWallet = walletDataService.getWalletByUUID(walletId);
//
//        assertEquals(expectedWallet, actualWallet);
//    }
//}
