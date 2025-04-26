package com.example.wallet_manager.controller;

import com.example.wallet_manager.dto.ErrorResponse;
import com.example.wallet_manager.dto.ValidationErrorResponse;
import com.example.wallet_manager.dto.Violation;
import com.example.wallet_manager.exception.WalletNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWalletNotFound(WalletNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("WALLET_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorResponse(violations));
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
        final Violation violations = new Violation(e.getMessage());
        return new ValidationErrorResponse(List.of(violations));
    }


//нет нужного поля
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
//        List<String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        ErrorResponse errorResponse = new ErrorResponse(
//                "JSON_PARSE_ERROR",
//                String.join("; ", errors)
//        );
//
//        return ResponseEntity.badRequest().body(errorResponse);
//    }

//не то значение в walletId, operationType
// amount:проверка на меньше 0,01
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException ex) {
//        Throwable mostSpecificCause = ex.getMostSpecificCause();
//        String technicalMessage = mostSpecificCause.getMessage();
//
////        String userFriendlyMessage = extractFriendlyMessage(technicalMessage);
//
//        return ResponseEntity.badRequest().body(
//                new ErrorResponse("JSON_PARSE_ERROR", technicalMessage)
//        );
//    }
//
//    private String extractFriendlyMessage(String technicalMessage) {
//        if (technicalMessage.contains("UUID")) {
//            return "Поле 'walletId' должно быть валидным UUID, например: '550e8400-e29b-41d4-a716-446655440000'";
//        }
//        if (technicalMessage.contains("OperationType")) {
//            return "Поле 'operationType' должно содержать одно из допустимых значений: 'DEPOSIT', 'WITHDRAW'";
//        }
//        if (technicalMessage.contains("BigDecimal")) {
//            return "Поле 'amount' должно быть числом больше 0.01";
//        }
//        return "Ошибка в формате JSON: " + technicalMessage;
//    }
}
