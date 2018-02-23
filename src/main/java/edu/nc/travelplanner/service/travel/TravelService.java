package edu.nc.travelplanner.service.travel;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import edu.nc.travelplanner.dao.*;
import edu.nc.travelplanner.dto.afterPickTree.CheckpointAfterPickTreeDto;
import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TravelService {

    @Autowired
    VkGeoNamesProvider vkGeoNamesProvider;

    @Autowired
    TravelDao travelDao;
    @Autowired
    CityDao cityDao;
    @Autowired
    CountryDao countryDao;
    @Autowired
    PlaceOfResidenceDao placeOfResidenceDao;
    @Autowired
    TypeOfResidenceDao typeOfResidenceDao;
    @Autowired
    ClientDao clientDao;

    public void saveTravelAfterPick(TravelAfterPickTreeDto pickDto){
        try {
            Travel travel = mapTravelAfterPickToTravel(pickDto);
            travelDao.saveTravel(travel);

            //set User
            saveTravelUserLink(pickDto, travel);
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
    }

    private void saveTravelUserLink(TravelAfterPickTreeDto pickDto, Travel travel) {
        Client client =clientDao.getClientById(pickDto.getClientId());
        if (client!=null){
            travel.addTravelForClient(new TravelForClient(){{
                setClient(client);
                setTravel(travel);
            }});
        }
    }

    private Travel mapTravelAfterPickToTravel(TravelAfterPickTreeDto pickDto) throws ClientException, ApiException {
        Travel travel = new Travel();

        //from CheckPoint
        CheckpointAfterPickTreeDto from = pickDto.getFrom();
        if (from!=null){
            CheckPoint fromCheckPoint = saveAndGetFromCheckPoint(pickDto);
            travel.addCheckPoint(fromCheckPoint);
        }

        return travel;
    }

    private CheckPoint saveAndGetFromCheckPoint(TravelAfterPickTreeDto pickDto) throws ClientException, ApiException {
        CheckPoint fromCheckPoint = new CheckPoint();

        Country countryById = saveAndGetCountry(pickDto);
        City cityById = saveAndGetCity(pickDto, countryById);

        PlaceOfResidence placeOfResidence = new PlaceOfResidence() {{
            setCity(cityById);
            setCountry(countryById);
            setTypeOfResidence(typeOfResidenceDao.getTypeOfResidenceById(TypeOfResidence.FROM_PLACE_ID));
        }};
        fromCheckPoint.setPlaceOfResidence(placeOfResidence);
        return fromCheckPoint;
    }

    private City saveAndGetCity(TravelAfterPickTreeDto pickDto, Country countryById) throws ClientException, ApiException {
        Long cityId = pickDto.getFrom().getCityId();
        City cityById = cityDao.getCityById(cityId);
        if (cityById==null){
            cityById = new City(){{
                setCityId(cityId);
                setCountry(countryById);
                setName(vkGeoNamesProvider.getCityNameById(cityId.intValue()));
            }};
            cityDao.saveCity(cityById);
        }
        return cityById;
    }

    private Country saveAndGetCountry(TravelAfterPickTreeDto pickDto) throws ClientException, ApiException {
        Integer countryId = pickDto.getFrom().getCountryId();
        Country countryById = countryDao.getCountryById(countryId);
        if (countryById==null){
            countryById = new Country(){{
                setCountryId(countryId);
                setName(vkGeoNamesProvider.getCountryNameById(countryId));
            }};
            countryDao.saveCountry(countryById);
        }

        return countryById;
    }
}
