package edu.nc.travelplanner.service.user;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import edu.nc.travelplanner.dao.ClientDao;
import edu.nc.travelplanner.dao.CountryDao;
import edu.nc.travelplanner.dto.profile.UserProfileDto;
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
    ClientDao clientDao;

    @Autowired
    CountryDao countryDao;

    @Autowired
    GeoNamesDbFiller geoNamesDbFiller;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserProfileDto getUserProfileById(Long userId){
        Client clientById = clientDao.getClientById(userId);
        if (clientById==null)
            return new UserProfileDto();

        return UserProfileDto.fromClient(clientById);
    }

    public UserProfileDto getUserProfileByName(String username){
        Client clientById = clientDao.getClientByLogin(username);
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
            Client client = clientDao.getClientByLogin(userProfileDto.getLogin());
            client = UserProfileDto.toClient(client, userProfileDto);

            if (userProfileDto.getPassword()!=null)
                client.setPassword(passwordEncoder.encode(userProfileDto.getPassword()));

            clientDao.saveClient(client);
            return UserProfileDto.fromClient(client);

        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return userProfileDto;
    }

}
