package com.example.travelapp.model;


import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinalResponseGetAllCities {
    private String city;
    private List<FinalResponse> placesDetails;
}