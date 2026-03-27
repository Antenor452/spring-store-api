package com.codewithmosh.store.controllers;


import com.codewithmosh.store.dtos.ErrorResponse;
import com.codewithmosh.store.exceptions.*;
import com.codewithmosh.store.payments.PaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ) {
        var errors = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Void> handleMethodArgumentTypeMismatchException() {
        return ResponseEntity.badRequest().build();
    }


    @ExceptionHandler({InvalidAuthenticationCredentialsException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorResponse<String>> handleInvalidAuthenticationCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponse<String>("Invalid Authentication credentials")
        );
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse<String>> handleResourceNotFoundException(
            ResourceNotFoundException exception
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse<String>(exception.getMessage())
        );
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<String>> handleMissingRequestBodyException(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse<String>("Required request body is missing")
        );
    }

    @ExceptionHandler(ForbiddenResourceException.class)
    public ResponseEntity<ErrorResponse<String>> handleForbiddenResourceException(ForbiddenResourceException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorResponse<String>(exception.getMessage())
        );
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse<String>> handleBadRequest(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse<String>(exception.getMessage())
        );
    }


    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse<String>> handlePaymentException(PaymentException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse<String>(exception.getMessage())
        );
    }
}
