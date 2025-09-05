package com.example.jobtracker_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Health Check", description = "Endpoints de test et de monitoring")
public class PingController {
    @GetMapping("/ping")
    @Operation(summary = "Ping de test", description = "Retourne un message pour vérifier que l’API fonctionne")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Pong 🏓 - API OK");
    }
}
