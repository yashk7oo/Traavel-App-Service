package com.example.travelapp.model;

public class MyResponse {
    private String responseField;

    // Getters and setters

    public String getResponseField() {
        return responseField;
    }

    public void setResponseField(String responseField) {
        this.responseField = responseField;
    }

    @Override
    public String toString() {
        return "MyResponse{" +
                "responseField='" + responseField + '\'' +
                '}';
    }
}