package com.github.cyfko.sc_verify.advice;

import io.github.cyfko.dverify.exceptions.DataExtractionException;
import io.github.cyfko.dverify.exceptions.JsonEncodingException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ControllerExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionAdvice.class);

    @ExceptionHandler
    public ResponseEntity<?> jsonEncodingHandler(JsonEncodingException ex, HttpServletRequest request) {
        String errorMessage = "The 'data' to sign is invalid. Expect a valid JSON object.";
        log.error("{} \n {}", errorMessage, ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
    }

    @ExceptionHandler
    public ResponseEntity<?> jsonEncodingException(NullPointerException ex, HttpServletRequest request) {
        final String errorMessage = "Expected to find 'duration: <int>' and 'data: <JSON object>' fields from the request body.";
        log.error("{} \n {}", errorMessage, ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
    }

    @ExceptionHandler
    public ResponseEntity<?> dataExtractionExceptionHandler(DataExtractionException ex, HttpServletRequest request) {
        String errorMessage = "Your token is either invalid or expired";
        log.error("{} \n {}", errorMessage, ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
    }

    @ExceptionHandler
    public ResponseEntity<?> classCastExceptionHandler(ClassCastException ex, HttpServletRequest request) {
        final String errorMessage = "Data format is not valid. Expected => 'duration: <int>', 'data: <JSON object>' and 'token: <string>'.";
        log.error("{} \n {}", errorMessage, ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of("error", errorMessage));
    }
}
