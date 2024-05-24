package com.example.travelapp.model;
import lombok.Builder;

import java.util.List;

@Builder
public class MyRequestBody {
    private List<Content> contents;
    private GenerationConfig generationConfig;

    // Getters and setters
    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public GenerationConfig getGenerationConfig() {
        return generationConfig;
    }

    public void setGenerationConfig(GenerationConfig generationConfig) {
        this.generationConfig = generationConfig;
    }
}

