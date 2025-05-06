package com.example.wallet_manager.service;

import com.example.wallet_manager.dto.WalletOperationRequest;
import com.example.wallet_manager.entity.Wallet;
import com.example.wallet_manager.exception.InsufficientFundsException;
import com.example.wallet_manager.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletOperationService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletDataService walletDataService;

    public WalletOperationService() {
    }

    public Wallet applyOperation(WalletOperationRequest walletOperationRequest) {

        Wallet wallet = walletDataService.getWalletByUUID(walletOperationRequest.getWalletId());

        validateOperation(walletOperationRequest, wallet);

        updateWalletBalance(walletOperationRequest, wallet);

        walletRepository.save(wallet);

        return wallet;
    }

    private void validateOperation(WalletOperationRequest walletOperationRequest, Wallet wallet) {

        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal amount = walletOperationRequest.getAmount();

        BigDecimal newBalance;

        switch (walletOperationRequest.getOperationType()) {
            case DEPOSIT:
                return;

            case WITHDRAW:
                newBalance = currentBalance.subtract(amount);
                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                    throw new InsufficientFundsException(wallet.getId());
                }
                break;
        }
    }

    private void updateWalletBalance(WalletOperationRequest walletOperationRequest, Wallet wallet) {

        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal amount = walletOperationRequest.getAmount();

        switch (walletOperationRequest.getOperationType()) {
            case DEPOSIT -> wallet.setBalance(currentBalance.add(amount));
            case WITHDRAW -> wallet.setBalance(currentBalance.subtract(amount));
        }
    }
}
