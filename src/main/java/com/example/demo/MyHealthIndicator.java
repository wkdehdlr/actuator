package com.example.demo;

import java.util.concurrent.atomic.AtomicReference;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyHealthIndicator implements HealthIndicator {

    private final AtomicReference<Health> healthRef = new AtomicReference<>(Health.up().build());

    @Override
    public Health health() {
        return healthRef.get();
    }

    public Health up() {
        Health up = Health.up().build();
        healthRef.set(up);
        return up;
    }

    public Health down() {
        Health down = Health.down().build();
        healthRef.set(down);
        return down;
    }
}
