package com.example.wallet_manager.controller;

import com.example.wallet_manager.dto.ValidationErrorResponse;
import com.example.wallet_manager.dto.Violation;
import com.example.wallet_manager.exception.InsufficientFundsException;
import com.example.wallet_manager.exception.WalletNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleWalletNotFound(WalletNotFoundException ex) {
        final Violation violations = new Violation(
                "walletId",
                ex.getMessage()
        );
        return new ValidationErrorResponse(List.of(violations));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse throwInsufficientFunds(InsufficientFundsException ex) {
        final Violation violations = new Violation(
                null,
                ex.getMessage()
        );
        return new ValidationErrorResponse(List.of(violations));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(
            HttpMessageNotReadableException e
    ) {
        Throwable mostSpecificCause = e.getMostSpecificCause();
        String messageExNotSupp = "Field is not supported";
        String messageExInvalidData = "Invalid data format";

        if (mostSpecificCause instanceof UnrecognizedPropertyException) {
            String unknownField = ((UnrecognizedPropertyException) mostSpecificCause).getPropertyName();

            final Violation violations = new Violation(
                    unknownField,
                    messageExNotSupp
            );
            return new ValidationErrorResponse(List.of(violations));
        }

        if (mostSpecificCause instanceof InvalidFormatException) {
            InvalidFormatException ex = (InvalidFormatException) mostSpecificCause;

            String fieldName = ex.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .findFirst()
                    .get();

            final Violation violations = new Violation(
                    fieldName,
                    messageExInvalidData
            );
            return new ValidationErrorResponse(List.of(violations));
        }

        final Violation violations = new Violation(e.getMessage());
        return new ValidationErrorResponse(List.of(violations));
    }
}
