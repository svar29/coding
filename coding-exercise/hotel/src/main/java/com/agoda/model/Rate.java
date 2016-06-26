package com.agoda.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rate {
    @JsonProperty
    private Double messages;
    @JsonProperty
    private Double seconds;

    public Rate() {
    }

    public Rate(Double messages, Double seconds) {
        this.messages = messages;
        this.seconds = seconds;
    }

    public Double getMessages() {
        return messages;
    }

    public Double getSeconds() {
        return seconds;
    }

}
