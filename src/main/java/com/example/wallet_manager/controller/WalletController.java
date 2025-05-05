package com.example.wallet_manager.controller;
import com.example.wallet_manager.dto.WalletOperationRequest;
import com.example.wallet_manager.entity.Wallet;
import com.example.wallet_manager.service.WalletOperationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class WalletController {
    public WalletController() {}

    @Autowired
    private WalletOperationService walletOperationService;

    @PostMapping(value = "/api/v1/wallet")
    public Wallet getWalletData(@Validated(WalletOperationRequest.WalletData.class) @RequestBody WalletOperationRequest walletOperationRequest) {

        return walletOperationService.applyOperation(walletOperationRequest);
    }
}
