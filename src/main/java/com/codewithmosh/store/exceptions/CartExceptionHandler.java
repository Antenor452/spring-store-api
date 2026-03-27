package com.codewithmosh.store.exceptions;


import com.codewithmosh.store.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CartExceptionHandler {
    ResponseEntity<ErrorResponse<String>> handleCartNotFoundException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse<String>("Cart not found ")
        );
    }
}
