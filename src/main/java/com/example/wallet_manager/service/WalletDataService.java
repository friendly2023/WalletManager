package com.example.wallet_manager.service;

import com.example.wallet_manager.entity.Wallet;
import com.example.wallet_manager.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletDataService {
    @Autowired
    private WalletRepository walletRepository;

    public WalletDataService() {
    }

    public Wallet getWalletByUUID(UUID walletId) {

        return walletRepository.getWalletByUUID(walletId);
    }
}
