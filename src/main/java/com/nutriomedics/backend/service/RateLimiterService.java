package com.nutriomedics.backend.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCKOUT_SECONDS = 60;

    private final ConcurrentHashMap<String, Attempt> attempts = new ConcurrentHashMap<>();

    private static class Attempt {
        int count = 1;
        Instant lockUntil = null;
    }

    public boolean isAllowed(String email) {
        Attempt attempt = attempts.get(email);

        if (attempt == null)
            return true;

        if (attempt.lockUntil != null && Instant.now().isBefore(attempt.lockUntil)) {
            return false;
        }

        if (attempt.lockUntil != null && Instant.now().isAfter(attempt.lockUntil)) {
            attempts.remove(email);
            return true;
        }

        return attempt.count < MAX_ATTEMPTS;
    }

    public void recordFailedAttempt(String email) {

        Attempt attempt = attempts.computeIfAbsent(email, k -> new Attempt());

        if (attempt.lockUntil != null && Instant.now().isAfter(attempt.lockUntil)) {
            attempts.remove(email);
            attempt = new Attempt();
        }

        attempt.count++;

        if (attempt.count >= MAX_ATTEMPTS) {
            attempt.lockUntil = Instant.now().plusSeconds(LOCKOUT_SECONDS);
        }

        attempts.put(email, attempt);
    }

    public void reset(String email) {
        attempts.remove(email);
    }
}