package edu.nc.travelplanner;

import edu.nc.travelplanner.dao.json.JsonFileUtil;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.TextResponse;
import edu.nc.travelplanner.model.source.filter.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class FilterUtilTests {


    @Test
    public void canGetCityFromJsonIfContainsInArray(){
        GetValueListIfContainsInPropertyFromJsonArray filter =
                new GetValueListIfContainsInPropertyFromJsonArray("/json/other/places_en.json",
        new LinkedList<ParameterMapperEntry>(){{
            this.add(new ParameterMapperEntry("code","location_id"));
            this.add(new ParameterMapperEntry("country_id","country_id"));
            this.add(new ParameterMapperEntry("city_id","city_id"));
        }},"name");

        Map<String, String> results = new LinkedHashMap<>();

        String moscow = filter.filter("Voronezh", results);

        return;


    }
}
