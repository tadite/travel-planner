package edu.nc.travelplanner.controller;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;
import edu.nc.travelplanner.service.travel.VkGeoNamesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class GeoDataController {

    @Autowired
    VkGeoNamesProvider vkGeoNamesProvider;

    @GetMapping(path = "/api/client/geo/country")
    public ResponseEntity getCountries() {

        try {
            return new ResponseEntity(vkGeoNamesProvider.getCountries(), HttpStatus.OK);
        } catch (DataProducerParseException | IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping(path = "/api/client/geo/city/{countryId}")
    public ResponseEntity getCitiesByQuery(@PathVariable Integer countryId) {

        try {
            return new ResponseEntity(vkGeoNamesProvider.findCitiesByName("", countryId), HttpStatus.OK);
        } catch (IOException | DataProducerParseException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping(path = "/api/client/geo/city/{countryId}/{query}")
    public ResponseEntity getCitiesByQuery(@PathVariable Integer countryId, @PathVariable String query) {

        try {
            return new ResponseEntity(vkGeoNamesProvider.findCitiesByName(query, countryId), HttpStatus.OK);
        } catch (IOException | DataProducerParseException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }
}
