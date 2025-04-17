package com.example.wallet_manager.service;

import com.example.wallet_manager.dto.WalletOperationRequest;
import com.example.wallet_manager.entity.Wallet;
import com.example.wallet_manager.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletOperationService {
    private final WalletRepository walletRepository;
    private final WalletDataService walletDataService;

    @Autowired
    public WalletOperationService(WalletRepository walletRepository,
                                  WalletDataService walletDataService) {
        this.walletRepository = walletRepository;
        this.walletDataService = walletDataService;
    }

    public Wallet applyOperation(WalletOperationRequest walletOperationRequest) {

        Wallet wallet = walletDataService.getWalletByUUID(walletOperationRequest.getValletId());


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
                    throw new IllegalArgumentException("Недостаточно средств на кошельке для списания.");
                }
                break;

            default:
                throw new UnsupportedOperationException("Неподдерживаемый тип операции");
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
