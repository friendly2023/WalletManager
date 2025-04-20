package com.example.wallet_manager.service;

import com.example.wallet_manager.entity.Wallet;
import com.example.wallet_manager.exception.WalletNotFoundException;
import com.example.wallet_manager.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletDataService {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletDataService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet getWalletByUUID(UUID walletId) {

        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
    }
}
