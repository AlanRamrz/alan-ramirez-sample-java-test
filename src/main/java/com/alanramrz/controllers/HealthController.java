package com.alanramrz.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/healthcheck")
    public ResponseEntity<Object> healthcheck() {
        return ResponseEntity.ok().build();
    }
}
