// Java Program to Demonstrate Department File

// Importing required package modules
package com.example.travelapp.entity;


import com.example.travelapp.model.FinalResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

// Class
public class CityInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cityId;
	private String cityName;
	@Lob
	@Column(nullable = false, name = "placeDetails", length = 100_000)
	private String placeDetails;
}
