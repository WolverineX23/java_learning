package org.example.hotchpotch.redis.exception;

public class RateLimiterException extends RuntimeException {

    public RateLimiterException(String message) {
        super(message);
    }

}
