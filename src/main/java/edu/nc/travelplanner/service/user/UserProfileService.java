package edu.nc.travelplanner.service.user;

import com.google.common.base.Optional;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import edu.nc.travelplanner.dto.profile.UserProfileDto;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;
import edu.nc.travelplanner.repository.CityRepository;
import edu.nc.travelplanner.repository.ClientRepository;
import edu.nc.travelplanner.repository.CountryRepository;
import edu.nc.travelplanner.service.travel.GeoNamesDbFiller;
import edu.nc.travelplanner.table.City;
import edu.nc.travelplanner.table.Client;
import edu.nc.travelplanner.table.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserProfileService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    GeoNamesDbFiller geoNamesDbFiller;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserProfileDto getUserProfileById(Long userId){
        Client clientById = clientRepository.findOne(userId);
        if (clientById==null)
            return new UserProfileDto();

        return UserProfileDto.fromClient(clientById);
    }

    public UserProfileDto getUserProfileByName(String username){
        Client clientById = clientRepository.findByLogin(username);
        if (clientById==null)
            return new UserProfileDto();

        return UserProfileDto.fromClient(clientById);
    }

    @Transactional
    public UserProfileDto updateUserProfile(UserProfileDto userProfileDto){
        try {
            if (userProfileDto.getCountryId()!=null && userProfileDto.getCityId()!=null) {
                Country country = geoNamesDbFiller.addOrGetCountryToDb(userProfileDto.getCountryId());
                City city = geoNamesDbFiller.addOrGetCityToDb(userProfileDto.getCityId(), country.getCountryId());
            }
            Client client = clientRepository.findByLogin(userProfileDto.getLogin());
            client = UserProfileDto.toClient(client, userProfileDto);
            client.setCountry(countryRepository.findOptionalByName(userProfileDto.getLogin()).orNull());
            client.setCity(cityRepository.findOne(userProfileDto.getCityId()));

            if (userProfileDto.getPassword()!=null)
                client.setPassword(passwordEncoder.encode(userProfileDto.getPassword()));

            clientRepository.save(client);
            return UserProfileDto.fromClient(client);

        } catch (ClientException e) {
            e.printStackTrace();
        } catch (DataProducerParseException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return userProfileDto;
    }

}
