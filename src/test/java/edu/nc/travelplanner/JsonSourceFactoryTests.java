package edu.nc.travelplanner;

import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.constant.DropDownListAction;
import edu.nc.travelplanner.model.factory.DefaultEnumMapper;
import edu.nc.travelplanner.model.factory.action.ActionJsonReader;
import edu.nc.travelplanner.model.factory.action.JsonActionFactory;
import edu.nc.travelplanner.model.factory.source.JsonSourceFactory;
import edu.nc.travelplanner.model.factory.source.SourceJsonReader;
import edu.nc.travelplanner.model.factory.source.SourceParseException;
import edu.nc.travelplanner.model.source.HttpSource;
import edu.nc.travelplanner.model.source.SourceType;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class JsonSourceFactoryTests {

    @Test
    public void canParseSourceFromJson() throws IOException, SourceParseException {
        //Array
        String testSourceName = "test-source";
        String testSourceJson = "{\"name\":\"test-source\",\"type\":\"http\",\"parameters\":{\"url\":\"vk.com\", \"description\":\"test-desc\"}}";

        SourceJsonReader mockSourceJsonReader = Mockito.mock(SourceJsonReader.class);
        Mockito.when(mockSourceJsonReader.getSourceJson(testSourceName)).thenReturn(testSourceJson);
        JsonSourceFactory jsonSourceFactory = new JsonSourceFactory(mockSourceJsonReader, new DefaultEnumMapper());

        //Act
        HttpSource httpSource = (HttpSource) jsonSourceFactory.createSource(testSourceName);

        //Assert
        Assert.assertEquals(testSourceName, httpSource.getName());
        Assert.assertEquals("test-desc", httpSource.getDescription());
        Assert.assertEquals("vk.com", httpSource.getUrl());
        Assert.assertEquals(SourceType.HTTP, httpSource.getType());
    }
}
