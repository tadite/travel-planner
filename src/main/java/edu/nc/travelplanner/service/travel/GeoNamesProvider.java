package edu.nc.travelplanner.service.travel;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

public interface GeoNamesProvider {
    String getCountryNameById(Integer id) throws ClientException, ApiException;
    String getCityNameById(Integer id) throws ClientException, ApiException;
}
