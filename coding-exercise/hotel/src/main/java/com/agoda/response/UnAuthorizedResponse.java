package com.agoda.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UnAuthorizedResponse {
    @JsonProperty
    private String message;

    public UnAuthorizedResponse() {
        this.message = "Please provide your api key for using this api.";
    }

}
