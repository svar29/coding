package com.agoda.core;

public interface RateLimit {
    boolean processMessage(Long nowSeconds);
    Long getBlockedTill();
    void setBlockedTill(Long blockedTill);
}
