package com.github.cyfko.sc_verify.controller;

import io.github.cyfko.dverify.DataSigner;
import io.github.cyfko.dverify.DataVerifier;
import io.github.cyfko.dverify.exceptions.DataExtractionException;
import io.github.cyfko.dverify.exceptions.JsonEncodingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/v0")
public class SignerController {
    private final DataSigner dataSigner;
    private final DataVerifier dataVerifier;

    public SignerController(DataSigner dataSigner, DataVerifier dataVerifier) {
        this.dataSigner = dataSigner;
        this.dataVerifier = dataVerifier;
    }

    @PostMapping("/sign")
    public ResponseEntity<?> signRequest(@RequestBody Map<String, Object> requestData, @RequestParam long seconds) {
        try {
            final String token = dataSigner.sign(requestData, Duration.ofSeconds(seconds));
            return ResponseEntity.ok(token);
        } catch (JsonEncodingException e) {
            return ResponseEntity.badRequest().body("The request data is invalid. Expect a valid JSON object");
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyRequest(@RequestBody String requestData) {
        try {
            final var token = dataVerifier.verify(requestData, Map.class);
            return ResponseEntity.ok(token);
        } catch (DataExtractionException e){
            return ResponseEntity.badRequest().body("Your token is either invalid or expired");
        }
    }
}
