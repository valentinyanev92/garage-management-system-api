package com.softuni.gms.web;

import com.softuni.gms.exception.InvoiceGenerationException;
import com.softuni.gms.exception.WhatsAppSendException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvoiceGenerationException.class)
    public ResponseEntity<String> handleInvoiceError(InvoiceGenerationException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Invoice generation failed: " + ex.getMessage());
    }

    @ExceptionHandler(WhatsAppSendException.class)
    public ResponseEntity<Map<String, String>> handleWhatsAppError(WhatsAppSendException ex) {

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of(
                        "error", "WhatsApp service error",
                        "message", ex.getMessage()
                ));
    }
}
