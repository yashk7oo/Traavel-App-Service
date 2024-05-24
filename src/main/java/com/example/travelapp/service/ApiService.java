package com.example.travelapp.service;

import com.example.travelapp.entity.CityInfo;
import com.example.travelapp.model.*;
import com.example.travelapp.repository.CityInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    CityInfoRepository cityInfoRepository;

    @Value("${gemini.url}")
    private String geminiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    ObjectMapper objectMapper = new ObjectMapper();

    public List<FinalResponse> makePostRequest(String city) throws JsonProcessingException {
        List<FinalResponse> finalResponseList = new java.util.ArrayList<>(List.of());

        CityInfo cityInfo = cityInfoRepository.findCityInfoByCityName(city);
        if (cityInfo != null) {
            Map<String, String> rawMap = objectMapper.readValue(cityInfo.getPlaceDetails(), Map.class);

            for (Map.Entry<String, String> entry : rawMap.entrySet()) {
                finalResponseList.add(new FinalResponse(entry.getKey(), entry.getValue()));
            }
            System.out.println("Serving request from DB");
        } else {
            String url = geminiUrl+"key="+ geminiApiKey;

            // Create the request body
            MyRequestBody requestBody = MyRequestBody.builder().contents(List.of(Content.builder().parts(List.of(Part.builder().text("List top 10 places to visit in "+ city + " with place as key and description as value. Do not add numbering to response text")
                    .build())).build())).generationConfig(GenerationConfig.builder().responseMimeType("application/json").build()).build();

            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity
            HttpEntity<MyRequestBody> requestEntity = new HttpEntity<>(requestBody, headers);

            // Send the POST request
            String response = restTemplate.postForObject(url, requestEntity, String.class);
            try {
                Response responseObj = objectMapper.readValue(response, Response.class);
                String text = responseObj.getCandidates().get(0).getContent().getParts().get(0).getText();

                cityInfoRepository.save(new CityInfo(null, city, text));

                // Convert JSON string to Map
                Map<String, String> rawMap = objectMapper.readValue(text, Map.class);

                for (Map.Entry<String, String> entry : rawMap.entrySet()) {
                    finalResponseList.add(new FinalResponse(entry.getKey(), entry.getValue()));
                }


            } catch (Exception e) {
                e.getMessage();
            }
        }
        return finalResponseList;
    }

    @Transactional
    public ResponseEntity deleteByCityName(String city) {
        CityInfo cityInfo = cityInfoRepository.findCityInfoByCityName(city);
        if(cityInfo == null) {
            return new ResponseEntity<>("Information for " + city + " does not exist in database",HttpStatus.BAD_REQUEST);
        } else {
            cityInfoRepository.deleteCityInfoByCityName(city);
            return new ResponseEntity(city + " and its related information has been deleted successfully", HttpStatus.OK);
        }
    }

    public ResponseEntity getDetailsForExistingCities() throws JsonProcessingException {
        List<FinalResponseGetAllCities> cityInfoResponseList = new ArrayList<>();
        Iterable<CityInfo> cityInfoList = cityInfoRepository.findAll();
        if(cityInfoList == null) {
            return new ResponseEntity("No information exist in DB", HttpStatus.BAD_REQUEST);
        } else {
            for(CityInfo cityInfo : cityInfoList) {
                Map<String, String> rawMap = objectMapper.readValue(cityInfo.getPlaceDetails(), Map.class);
                List<FinalResponse> places = new ArrayList<>();
                for (Map.Entry<String, String> entry : rawMap.entrySet()) {
                    places.add(new FinalResponse(entry.getKey(), entry.getValue()));
                }
                cityInfoResponseList.add(new FinalResponseGetAllCities(cityInfo.getCityName(), places));
            }
        }
        return new ResponseEntity(cityInfoResponseList, HttpStatus.OK);
    }
}