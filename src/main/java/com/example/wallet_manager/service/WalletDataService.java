package com.example.wallet_manager.service;

import com.example.wallet_manager.entity.Wallet;
import com.example.wallet_manager.repository.WalletRepository;
import com.example.wallet_manager.service.verification.UUIDValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletDataService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UUIDValidator uuidValidator;

    public WalletDataService() {
    }

    public Wallet getWalletByUUID(UUID walletId) {

        return walletRepository.getWalletByUUID(walletId);
    }

    public Wallet getWalletByStringUUID(String walletId) {

        uuidValidator.validate(walletId);
        return walletRepository.getWalletByUUID(UUID.fromString(walletId));
    }
}
