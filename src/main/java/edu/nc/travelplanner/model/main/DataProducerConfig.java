package edu.nc.travelplanner.model.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataProducerConfig {

    @Autowired
    ObjectMapper objectMapper;

    public DataProducerConfig(PathMapper pathMapper, ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        map = objectMapper.readValue(new File(PathUtil.getPathInUserDir(pathMapper.getResultMapperConfigPath())), HashMap.class);
    }

    private final Map<String, String> map;

    public DataProducerConfig(@Autowired PathMapper pathMapper) throws IOException {
        map = objectMapper.readValue(new File(PathUtil.getPathInUserDir(pathMapper.getResultMapperConfigPath())), HashMap.class);
    }

    public String getDataProducerNameForKey(String key){
        return map.get(key);
    }

    public String getAllCountriesDataProducerName(){
        return map.get("vk-all-countries");
    }

    public String getCitiesByCountryIdAndQueryName(){
        return map.get("vk-cities-by-countryid-and-query");
    }
}
