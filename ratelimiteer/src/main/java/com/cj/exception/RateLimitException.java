package com.cj.exception;

public class RateLimitException extends Exception {
    public RateLimitException(String message) {
        super(message);
    }
}
