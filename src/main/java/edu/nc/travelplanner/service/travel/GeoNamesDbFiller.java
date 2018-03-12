package edu.nc.travelplanner.service.travel;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;
import edu.nc.travelplanner.table.City;
import edu.nc.travelplanner.table.Country;

public interface GeoNamesDbFiller {
    Country addOrGetCountryToDb(Integer countryId) throws ClientException, ApiException, DataProducerParseException;
    City addOrGetCityToDb(Long cityId, Integer countryId) throws ClientException, ApiException, DataProducerParseException;
}
