package edu.nc.travelplanner.jsonDao;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.dao.json.SourceApiDto;
import edu.nc.travelplanner.dao.json.implementations.JsonSourceDao;
import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.source.FileSourceJsonReader;
import edu.nc.travelplanner.model.factory.source.SourceJsonReader;
import edu.nc.travelplanner.model.source.SourceType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.BDDMockito.given;

public class JsonSourceDaoTests {

    private SourceJsonReader mockSourceJsonReader;
    private JsonSourceDao jsonSourceDao;
    private String jsonSourceName;
    private String jsonSourceJson;

    @Before
    public void init() throws IOException {
        mockSourceJsonReader = Mockito.mock(SourceJsonReader.class);

        jsonSourceName = "test-source";
        jsonSourceJson = "{\"name\":\"vk-getCitiesByCountryId\",\"type\":\"http\",\"parameters\":{\"url\":\"https://api.vk.com/method/database.getCities\", \"params\":[\"country_id\"], \"description\":\"getCitiesByCountryId default\"}}";

        Mockito.when(mockSourceJsonReader.getSourceJson(jsonSourceName)).thenReturn(jsonSourceJson);

        jsonSourceDao= new JsonSourceDao(mockSourceJsonReader, new PathMapper(), new ObjectMapper());
    }

    @Test
    public void canGet() throws IOException {
        //Array

        //Act
        SourceApiDto sourceApiDto = jsonSourceDao.get(jsonSourceName);

        //Assert
        Assert.assertEquals("vk-getCitiesByCountryId", sourceApiDto.getName());
        Assert.assertEquals("getCitiesByCountryId default", sourceApiDto.getDescription());
        Assert.assertEquals("https://api.vk.com/method/database.getCities", sourceApiDto.getUrl());
        Assert.assertEquals(1, sourceApiDto.getParams().size());
        Assert.assertEquals("country_id", sourceApiDto.getParams().get(0));
    }

    @Test
    public void canGetAll() throws IOException {
        //Array

        //Act

        //Assert
    }

    @Test(expected = UnsupportedOperationException.class)
    public void canSaveAndDelete() throws IOException {
        //Array
        jsonSourceDao = new JsonSourceDao(new FileSourceJsonReader(new PathMapper()), new PathMapper(), new ObjectMapper());
        SourceApiDto sourceApiDto = new SourceApiDto();
        sourceApiDto.setName("test-name");
        sourceApiDto.setUrl("test-url");
        sourceApiDto.setDescription("test-desc");
        sourceApiDto.setParams(new LinkedList<String>(){{add("test-param");}});
        sourceApiDto.setType(SourceType.HTTP);

        //Act
        SourceApiDto savedSourceApiDto = jsonSourceDao.save(sourceApiDto);

        //Assert
        Assert.assertEquals(savedSourceApiDto.getName(), sourceApiDto.getName());
        Assert.assertEquals(savedSourceApiDto.getParams().get(0), sourceApiDto.getParams().get(0));
        Assert.assertEquals(savedSourceApiDto.getDescription(), sourceApiDto.getDescription());
        Assert.assertEquals(savedSourceApiDto.getUrl(), sourceApiDto.getUrl());
        Assert.assertEquals(savedSourceApiDto.getType(), sourceApiDto.getType());

        jsonSourceDao.delete(sourceApiDto.getName());
        SourceApiDto notExistedSourceApiDto=null;

        notExistedSourceApiDto = jsonSourceDao.get(sourceApiDto.getName());
    }

}
