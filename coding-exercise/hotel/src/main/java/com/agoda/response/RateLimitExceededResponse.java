package com.agoda.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RateLimitExceededResponse {
    @JsonProperty
    private String message;

    public RateLimitExceededResponse() {
        this.message = "You have exceeded maximum allowed usage. Please try after 5 minutes";
    }

}
