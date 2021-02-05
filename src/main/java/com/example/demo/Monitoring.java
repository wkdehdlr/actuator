package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monitor/l7check")
public class Monitoring {
    private final MyHealthIndicator myHealthIndicator;

    @GetMapping
    public ResponseEntity<Health> health() {
        Health health = myHealthIndicator.health();
        boolean isUp = health.getStatus().equals(Status.UP);
        return ResponseEntity.status(isUp ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE).body(health);
    }

    @GetMapping("/start")
    public ResponseEntity<Health> up() {
        return ResponseEntity.ok(myHealthIndicator.up());
    }

    @GetMapping("/stop")
    public ResponseEntity<Health> down() {
        return ResponseEntity.ok(myHealthIndicator.down());
    }
}
