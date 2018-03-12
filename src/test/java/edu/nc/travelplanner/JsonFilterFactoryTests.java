package edu.nc.travelplanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.factory.DefaultEnumMapper;
import edu.nc.travelplanner.model.factory.dataproducer.ResponseFilterDto;
import edu.nc.travelplanner.model.factory.filter.DefaultResponseFilterFactory;
import edu.nc.travelplanner.model.factory.filter.FilterParseException;
import edu.nc.travelplanner.model.factory.source.SourceParseException;
import edu.nc.travelplanner.model.source.FilterType;
import edu.nc.travelplanner.model.source.filter.JsonPathResponseFilter;
import edu.nc.travelplanner.model.source.filter.ListToMapJsonResponseFilter;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class JsonFilterFactoryTests {

    //@Test
    public void canParseJsonPathFilterFromJson() throws IOException, SourceParseException, FilterParseException {
        //Array
        String filterJson = "{\"type\":\"json_path\",\"parameters\":{\"expression\":\"test-exp\"}}";

        DefaultResponseFilterFactory defaultJsonFilterParser = new DefaultResponseFilterFactory(new DefaultEnumMapper());

        //Act
        JsonPathResponseFilter responseFilter = (JsonPathResponseFilter)defaultJsonFilterParser.create(new ObjectMapper().readValue(filterJson, ResponseFilterDto.class));

        //Assert
        Assert.assertEquals(FilterType.JSON_PATH, responseFilter.getType());
        Assert.assertEquals("test-exp", responseFilter.getExpression());
    }

    //@Test
    public void canParseListToMapFilterFromJson() throws IOException, SourceParseException, FilterParseException {
        //Array
        String filterJson = "{\"type\":\"list_to_map\",\"parameters\":{\"keyName\":\"test-keyName\", \"valueName\":\"test-valueName\"}}";

        DefaultResponseFilterFactory defaultJsonFilterParser = new DefaultResponseFilterFactory(new DefaultEnumMapper());

        //Act
        ListToMapJsonResponseFilter responseFilter = (ListToMapJsonResponseFilter)defaultJsonFilterParser.create(new ObjectMapper().readValue(filterJson, ResponseFilterDto.class));

        //Assert
        Assert.assertEquals(FilterType.LIST_TO_MAP, responseFilter.getType());
        Assert.assertEquals("test-keyName", responseFilter.getKeyName());
        Assert.assertEquals("test-valueName", responseFilter.getValueName());
    }

    //TODO: tests for another filters
}
