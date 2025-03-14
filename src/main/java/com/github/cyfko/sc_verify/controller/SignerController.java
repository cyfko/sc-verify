package com.github.cyfko.sc_verify.controller;

import com.github.cyfko.sc_verify.dto.CommandDTO;
import com.github.cyfko.sc_verify.dto.QueryDTO;
import io.github.cyfko.dverify.DataSigner;
import io.github.cyfko.dverify.DataVerifier;
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
    public ResponseEntity<?> signRequest(@RequestBody CommandDTO command) {
        final String token = dataSigner.sign(command.getData(), Duration.ofSeconds(command.getDuration()));
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyRequest(@RequestBody QueryDTO query) {
        final var data = dataVerifier.verify(query.getToken(), Object.class);
        return ResponseEntity.ok(Map.of("data", data));
    }
}
