package com.example.travelapp.controller;

import com.example.travelapp.model.FinalResponse;
import com.example.travelapp.service.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TravelApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/getPlacesForCity/{city}")
    public List<FinalResponse> getPlacesForCity(@PathVariable String city) throws JsonProcessingException {
        return apiService.makePostRequest(city.toUpperCase());
    }

    @DeleteMapping("/deleteCity/{city}")
    public ResponseEntity deleteCityInformation(@PathVariable String city) {
        return apiService.deleteByCityName(city.toUpperCase());
    }

    @GetMapping("/getDetailsForExistingCities")
    public ResponseEntity getAndPost() throws JsonProcessingException {
        return apiService.getDetailsForExistingCities();
    }
}