package com.example.wallet_manager.service.verification;

import com.example.wallet_manager.exception.InvalidUUIDFormatException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDValidator extends RuntimeException {
    public void validate(String uuidString) {
        try {
            System.out.println(uuidString);
            UUID.fromString(uuidString);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUUIDFormatException();
        }
    }
}

