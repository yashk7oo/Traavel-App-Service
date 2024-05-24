package com.example.travelapp.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    // Getters and setters
    private List<Part> parts;
    private String role;

}
