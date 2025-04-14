package com.example.wallet_manager.service;

import com.example.wallet_manager.dto.WalletOperationRequest;
import com.example.wallet_manager.exception.WalletNotFoundException;
import com.example.wallet_manager.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletExistenceValidatorService {
    private final WalletRepository walletRepository;

    @Autowired

    public WalletExistenceValidatorService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void validate(WalletOperationRequest walletOperationRequest) {

        UUID walletId = walletOperationRequest.getId();

        if (!walletRepository.existsById(walletId)) {
            throw new WalletNotFoundException(walletId);
        }
    }
}
