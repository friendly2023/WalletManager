package com.example.wallet_manager.controller;

import com.example.wallet_manager.dto.WalletOperationRequest;
import com.example.wallet_manager.entity.Wallet;
import com.example.wallet_manager.service.WalletDataService;
import com.example.wallet_manager.service.WalletOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Validated
public class WalletController {

    @Autowired
    private WalletOperationService walletOperationService;

    @Autowired
    private WalletDataService walletDataService;

    public WalletController() {
    }

    @PostMapping(value = "/api/v1/wallet")
    public Wallet getWalletDataAfterTheChanges(@Validated(WalletOperationRequest.WalletData.class) @RequestBody WalletOperationRequest walletOperationRequest) {
//todo: удалить вывод
        return walletOperationService.applyOperation(walletOperationRequest);
    }

    @GetMapping("/api/v1/wallets/{walletId}")
    public Wallet getWalletById(@PathVariable String walletId) {

        return walletDataService.getWalletByStringUUID(walletId);
    }
}
