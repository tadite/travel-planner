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
import edu.nc.travelplanner.service.travel.VkGeoNamesProvider;
import edu.nc.travelplanner.table.City;
import edu.nc.travelplanner.table.Client;
import edu.nc.travelplanner.table.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserProfileService {

    @Value("${travelplanner.requestAttemptsCount}")
    private String requestAttemtsCount;

    @Value("${travelplanner.requestWaitAfterFailSecs}")
    private String requestWaitAfterFailSecs;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    VkGeoNamesProvider vkGeoNamesProvider;

    @Autowired
    PasswordEncoder passwordEncoder;


    public UserProfileDto getUserProfileById(Long userId) {
        Client clientById = clientRepository.findOne(userId);
        if (clientById == null)
            return new UserProfileDto();

        return UserProfileDto.fromClient(clientById);
    }

    public UserProfileDto getUserProfileByName(String username) {
        Client clientById = clientRepository.findByLogin(username);
        if (clientById == null)
            return new UserProfileDto();

        return UserProfileDto.fromClient(clientById);
    }

    @Transactional
    public UserProfileDto updateUserProfile(UserProfileDto userProfileDto) {

        Country country = null;
        City city = null;

        if (userProfileDto.getCountryId() != null && userProfileDto.getCityId() != null) {
            String countryName = getCountryNameById(userProfileDto.getCountryId());
            String cityName = getCityNameById(userProfileDto.getCityId().intValue());
            if (countryName != null && cityName != null) {
                country = countryRepository.save(new Country(countryName, userProfileDto.getCountryId()));
                city = cityRepository.save(new City(cityName, country, userProfileDto.getCityId()));
            }
        }
        Client client = clientRepository.findByLogin(userProfileDto.getLogin());
        client = UserProfileDto.toClient(client, userProfileDto);
        client.setCountry(country);
        client.setCity(city);

        if (userProfileDto.getPassword() != null)
            client.setPassword(passwordEncoder.encode(userProfileDto.getPassword()));
        Client savedClient = clientRepository.save(client);

        return UserProfileDto.fromClient(savedClient);
    }

    private String getCityNameById(Integer cityId) {
        int triesLeft = Integer.valueOf(requestAttemtsCount);
        int waitAfterFail = Integer.valueOf(requestWaitAfterFailSecs);

        while (triesLeft > 0) {
            try {
                return vkGeoNamesProvider.getCityNameById(cityId);
            } catch (Exception e) {
                e.printStackTrace();
                triesLeft--;
                try {
                    Thread.sleep(waitAfterFail);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    private String getCountryNameById(Integer countryId) {
        int triesLeft = Integer.valueOf(requestAttemtsCount);
        int waitAfterFail = Integer.valueOf(requestWaitAfterFailSecs);

        while (triesLeft > 0) {
            try {
                return vkGeoNamesProvider.getCountryNameById(countryId);
            } catch (Exception e) {
                e.printStackTrace();
                triesLeft--;
                try {
                    Thread.sleep(waitAfterFail);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

}
