package edu.nc.travelplanner.service.travel;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.util.Map;

public interface GeoNamesProvider {
    String getCountryNameById(Integer id) throws ClientException, ApiException;
    String getCityNameById(Integer id) throws ClientException, ApiException;
    Map<Integer, String> findCitiesByName(String name, Integer countryId) throws ClientException, ApiException;
    Map<Integer, String> getCountries() throws ClientException, ApiException;
}
