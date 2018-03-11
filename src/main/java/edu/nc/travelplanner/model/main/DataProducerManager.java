package edu.nc.travelplanner.model.main;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public DataProducerManager() {
    }

    public DataProducerManager(DataProducerFactory dataProducerFactory, DataProducerConfig dataProducerConfig, ObjectMapper objectMapper) {
        this.dataProducerFactory = dataProducerFactory;
        this.dataProducerConfig = dataProducerConfig;
        this.objectMapper=objectMapper;
    }

    public Map<String, String> getAllCountriesMap() throws DataProducerParseException, IOException {
        return objectMapper.readValue(dataProducerFactory
                .createDataProducer(
                        dataProducerConfig.getAllCountriesDataProducerName()).send(new ArrayList<>()).getRawData(), HashMap.class);
    }

    public Map<String, String> getAllCitiesByCountryIdAndQuery(Long countryId, String query) throws DataProducerParseException, IOException {
        return objectMapper.readValue(dataProducerFactory
                .createDataProducer(dataProducerConfig.getCitiesByCountryIdAndQueryName())
                .send(new ArrayList<PickResult>(){{
                    this.add(new PickResult("country_id", countryId));
                    this.add(new PickResult("query", query));
                }}).getRawData(), HashMap.class);
    }
}
