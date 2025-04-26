package com.example.wallet_manager.controller;

import com.example.wallet_manager.dto.WalletOperationRequest;
import com.example.wallet_manager.entity.Wallet;
import com.example.wallet_manager.service.WalletDataService;
import com.example.wallet_manager.service.WalletOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    private final WalletDataService walletDataService;
    private final WalletOperationService walletOperationService;

    @Autowired
    public WalletController(WalletDataService walletDataService,
                            WalletOperationService walletOperationService) {
        this.walletDataService = walletDataService;
        this.walletOperationService = walletOperationService;
    }

    @PostMapping(value = "/")
    public Wallet getWalletData(@Validated(WalletOperationRequest.WalletData.class) @RequestBody WalletOperationRequest walletOperationRequest) {

        return walletOperationService.applyOperation(walletOperationRequest);
    }
}
