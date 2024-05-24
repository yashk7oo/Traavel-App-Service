package com.example.travelapp.repository;

import com.example.travelapp.entity.CityInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
 
// Annotation
@Repository
 
// Interface extending CrudRepository
public interface CityInfoRepository
    extends CrudRepository<CityInfo, Long> {

    CityInfo findCityInfoByCityName(String name);

    void deleteCityInfoByCityName(String name);
}