package edu.nc.travelplanner.service.travel;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import edu.nc.travelplanner.dao.CityDao;
import edu.nc.travelplanner.dao.CountryDao;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;
import edu.nc.travelplanner.table.City;
import edu.nc.travelplanner.table.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VkGeoNamesDbFiller implements GeoNamesDbFiller {

    @Autowired
    VkGeoNamesProvider vkGeoNamesProvider;

    @Autowired
    CountryDao countryDao;

    @Autowired
    CityDao cityDao;

    @Override
    public Country addOrGetCountryToDb(Integer countryId) throws ClientException, ApiException, DataProducerParseException {
        if (countryId==null)
            return null;

        Country countryById = countryDao.getCountryById(countryId);

        if (countryById==null)
        {
            countryById = new Country();
            countryById.setName(vkGeoNamesProvider.getCountryNameById(countryId));
            countryById.setCountryId(countryId);
            countryDao.saveCountry(countryById);
        }

        return countryById;
    }

    @Override
    public City addOrGetCityToDb(Long cityId, Integer countryId) throws DataProducerParseException {
        if (countryId==null || cityId==null)
            return null;

        City cityById = cityDao.getCityById(cityId);

        if (cityById==null)
        {
            cityById = new City();
            cityById.setName(vkGeoNamesProvider.getCityNameById(cityId.intValue()));
            cityById.setCityId(cityId);
            cityById.setCountryId(countryId);
            cityDao.saveCity(cityById);
        }

        return cityById;
    }
}
