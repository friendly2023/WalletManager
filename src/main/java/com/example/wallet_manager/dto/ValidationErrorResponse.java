package com.example.wallet_manager.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

@JsonView
public class ValidationErrorResponse {

    @JsonView
    private final List<Violation> violations;

    public ValidationErrorResponse(List<Violation> violations) {
        this.violations = violations;
    }
}