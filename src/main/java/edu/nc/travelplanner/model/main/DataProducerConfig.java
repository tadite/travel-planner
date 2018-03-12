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
    private final Map<String, String> map;

    @Autowired
    public DataProducerConfig(ObjectMapper objectMapper, PathMapper pathMapper) throws IOException {
        map = objectMapper.readValue(new File(PathUtil.getPathInUserDir(pathMapper.getResultMapperConfigPath())), HashMap.class);
    }

    public String getDataProducerNameForKey(String key){
        return map.get(key);
    }

    public String getAllCountriesDataProducerName(){
        return map.get("all-countries");
    }

    public String getCitiesByCountryIdAndQueryName(){
        return map.get("cities-by-countryid-and-query");
    }

    public String getCityNameByCityId(){
        return map.get("cityName-by-cityId");
    }

    public String getCountryNameByCountryId(){
        return map.get("countryName-by-countryId");
    }
}
