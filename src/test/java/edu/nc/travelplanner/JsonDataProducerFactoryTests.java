package edu.nc.travelplanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.factory.DefaultEnumMapper;
import edu.nc.travelplanner.model.factory.EnumMapper;
import edu.nc.travelplanner.model.factory.dataproducer.*;
import edu.nc.travelplanner.model.factory.filter.DefaultResponseFilterFactory;
import edu.nc.travelplanner.model.factory.source.JsonSourceFactory;
import edu.nc.travelplanner.model.factory.source.SourceFactory;
import edu.nc.travelplanner.model.factory.source.SourceJsonReader;
import edu.nc.travelplanner.model.source.FilterType;
import edu.nc.travelplanner.model.source.HttpSource;
import edu.nc.travelplanner.model.source.Source;
import edu.nc.travelplanner.model.source.SourceType;
import edu.nc.travelplanner.model.source.dataproducer.DataProducer;
import edu.nc.travelplanner.model.source.dataproducer.DefaultDataProducer;
import edu.nc.travelplanner.model.source.filter.JsonPathResponseFilter;
import edu.nc.travelplanner.model.source.filter.ListToMapJsonResponseFilter;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;


public class JsonDataProducerFactoryTests {

    //@Test
    public void canParseDataProducerFromJson() throws IOException, DataProducerParseException {
        //Array
        String sourceName = "test-source";

        String dataProducerName = "test-name";
        String jsonPathFilterExpression = "$.response[*]";
        FilterType jsonPathFilterType = FilterType.JSON_PATH;
        FilterType listToMapFilterType = FilterType.LIST_TO_MAP;
        String valueName = "test-valueName";
        String keyName = "test-keyName";
        String dataProducerJson = "{\"name\":\""+dataProducerName+"\",\"filters\":[{\"type\":\"json_path\",\"parameters\":{\"expression\":\""+jsonPathFilterExpression+"\"}},{\"type\":\"list_to_map\",\"parameters\":{\"valueName\":\""+valueName+"\",\"keyName\":\""+keyName+"\"}}],\"source\":\""+sourceName+"\"}";

        String sourceUrl = "vk.com";
        String sourceDescription = "test-desc";
        SourceType sourceType = SourceType.HTTP;
        String sourceJson = "{\"name\":\""+sourceName+"\",\"type\":\"http\",\"parameters\":{\"url\":\""+sourceUrl+"\", \"description\":\""+sourceDescription+"\"}}";
        Map<String, String> sourceMap = new HashMap<String, String>(){{put(sourceName, sourceJson);}};

        DefaultEnumMapper enumMapper = new DefaultEnumMapper();
        DefaultResponseFilterFactory defaultResponseFilterFactory = new DefaultResponseFilterFactory(enumMapper);

        DataProducerJsonReader mockDataProducerJsonReader = mock(DataProducerJsonReader.class);
        when(mockDataProducerJsonReader.getDataProducerJson(dataProducerName)).thenReturn(dataProducerJson);

        SourceJsonReader mockSourceJsonReader = mock(SourceJsonReader.class);
        for (Map.Entry<String, String> entry : sourceMap.entrySet())
            when(mockSourceJsonReader.getSourceJson(entry.getKey())).thenReturn(entry.getValue());

        SourceFactory sourceFactory = new JsonSourceFactory(mockSourceJsonReader, enumMapper);

        SenderFactory senderFactory = new DefaultSenderFactory(enumMapper);

        JsonDataProducerFactory jsonDataProducerFactory = new JsonDataProducerFactory(defaultResponseFilterFactory, mockDataProducerJsonReader, sourceFactory, senderFactory);

        //Act
        DefaultDataProducer dataProducer = (DefaultDataProducer)jsonDataProducerFactory.createDataProducer(dataProducerName);

        //Assert
        Assert.assertEquals(dataProducerName, dataProducer.getName());
        Assert.assertEquals(sourceUrl, dataProducer.getSource().getUrl());
        Assert.assertEquals(sourceName, dataProducer.getSource().getName());
        Assert.assertEquals(sourceType, dataProducer.getSource().getType());

        JsonPathResponseFilter jsonPathResponseFilter = (JsonPathResponseFilter)dataProducer.getResponseFilters().get(0);
        Assert.assertEquals(jsonPathFilterExpression, jsonPathResponseFilter.getExpression());
        Assert.assertEquals(jsonPathFilterType, jsonPathResponseFilter.getType());

        ListToMapJsonResponseFilter listToMapJsonResponseFilter = (ListToMapJsonResponseFilter)dataProducer.getResponseFilters().get(1);
        Assert.assertEquals(valueName, listToMapJsonResponseFilter.getValueName());
        Assert.assertEquals(keyName, listToMapJsonResponseFilter.getKeyName());
        Assert.assertEquals(listToMapFilterType, listToMapJsonResponseFilter.getType());
    }

}
