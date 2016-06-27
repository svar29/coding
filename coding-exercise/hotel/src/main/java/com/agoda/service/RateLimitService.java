package com.agoda.service;

public interface RateLimitService {
    boolean validate(String apiKey);
    boolean validate(String apiKey, Long currentTimeInMillis);
}
