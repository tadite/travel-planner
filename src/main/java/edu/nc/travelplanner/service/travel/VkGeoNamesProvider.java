package edu.nc.travelplanner.service.travel;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.queries.database.DatabaseGetCountriesByIdQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VkGeoNamesProvider implements GeoNamesProvider {

    @Autowired
    VkApiClient vkApiClient;

    @Autowired
    ServiceActor serviceActor;


    //TODO: implement vk
    @Override
    public String getCountryNameById(Integer id) throws ClientException, ApiException {
        return vkApiClient.database().getCountriesById(serviceActor).countryIds(id).execute().get(0).getTitle();
    }

    @Override
    public String getCityNameById(Integer id) throws ClientException, ApiException {
        return vkApiClient.database().getCitiesById(serviceActor).cityIds(id).execute().get(0).getTitle();
    }
}