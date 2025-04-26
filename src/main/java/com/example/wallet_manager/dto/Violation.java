package com.example.wallet_manager.dto;

import com.fasterxml.jackson.annotation.JsonView;

@JsonView
public class Violation {
    @JsonView
    private String fieldName;
    @JsonView
    private final String message;
    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public Violation(String message) {
        this.message = message;
    }
}