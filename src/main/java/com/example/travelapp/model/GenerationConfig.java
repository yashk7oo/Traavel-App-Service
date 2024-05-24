package com.example.travelapp.model;

import lombok.Builder;

@Builder
public class GenerationConfig {
    private String responseMimeType;

    // Getters and setters
    public String getResponseMimeType() {
        return responseMimeType;
    }

    public void setResponseMimeType(String responseMimeType) {
        this.responseMimeType = responseMimeType;
    }
}
