package com.example.travelapp.model;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinalResponse {
    private String place;
    private String placeDescription;
}