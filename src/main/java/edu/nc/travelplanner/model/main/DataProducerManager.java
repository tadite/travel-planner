package edu.nc.travelplanner.model.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.exception.DataProducerSendException;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerFactory;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataProducerManager {

    @Autowired
    DataProducerFactory dataProducerFactory;

    @Autowired
    DataProducerConfig dataProducerConfig;

    @Autowired
    ObjectMapper objectMapper;

    public Map<Integer, String> getAllCountriesMap() throws DataProducerParseException, IOException {
        try {
            return objectMapper.readValue(dataProducerFactory
                    .createDataProducer(
                            dataProducerConfig.getAllCountriesDataProducerName()).send(new ArrayList<>()).getRawData(), HashMap.class);
        } catch (DataProducerSendException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Integer, String> getAllCitiesByCountryIdAndQuery(Integer countryId, String query) throws DataProducerParseException, IOException {
        try {
            return objectMapper.readValue(dataProducerFactory
                    .createDataProducer(dataProducerConfig.getCitiesByCountryIdAndQueryName())
                    .send(new ArrayList<PickResult>() {{
                        this.add(new PickResult("country_id", countryId));
                        this.add(new PickResult("query", query));
                    }}).getRawData(), HashMap.class);
        } catch (DataProducerSendException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCityNameById(Integer id) throws DataProducerParseException {
        try {
            return dataProducerFactory
                    .createDataProducer(dataProducerConfig.getCityNameByCityId()).send(new ArrayList<PickResult>() {{
                this.add(new PickResult("city_id", id));
            }}).getRawData();
        } catch (DataProducerSendException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCountryNameById(Integer id) throws DataProducerParseException {
        try {
            return dataProducerFactory
                    .createDataProducer(dataProducerConfig.getCountryNameByCountryId()).send(new ArrayList<PickResult>() {{
                this.add(new PickResult("country_id", id));
            }}).getRawData();
        } catch (DataProducerSendException e) {
            e.printStackTrace();
        }
        return null;
    }
}
