package edu.nc.travelplanner.service.travel;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;

import java.io.IOException;
import java.util.Map;

public interface GeoNamesProvider {
    String getCountryNameById(Integer id) throws ClientException, ApiException, DataProducerParseException;
    String getCityNameById(Integer id) throws ClientException, ApiException, DataProducerParseException;
    Map<Integer, String> findCitiesByName(String name, Integer countryId) throws ClientException, ApiException, IOException, DataProducerParseException;
    Map<Integer, String> getCountries() throws ClientException, ApiException, IOException, DataProducerParseException;
}
