package edu.nc.travelplanner.service.travel;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.base.BaseObject;
import com.vk.api.sdk.queries.database.DatabaseGetCountriesByIdQuery;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;
import edu.nc.travelplanner.model.main.DataProducerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class VkGeoNamesProvider implements GeoNamesProvider {

    @Autowired
    VkApiClient vkApiClient;

    @Autowired
    ServiceActor serviceActor;

    @Autowired
    DataProducerManager dataProducerManager;


    //TODO: implement vk
    @Override
    public String getCountryNameById(Integer id) throws DataProducerParseException {
        return dataProducerManager.getCountryNameById(id);
    }

    @Override
    public String getCityNameById(Integer id) throws DataProducerParseException {
        return dataProducerManager.getCityNameById(id);
    }

    @Override
    public Map<Integer, String> findCitiesByName(String name, Integer countryId) throws IOException, DataProducerParseException {
        return dataProducerManager.getAllCitiesByCountryIdAndQuery(countryId,name);
    }

    @Override
    public Map<Integer, String> getCountries() throws IOException, DataProducerParseException {
        return dataProducerManager.getAllCountriesMap();
    }
}
