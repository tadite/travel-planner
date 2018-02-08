package edu.nc.travelplanner;

import edu.nc.travelplanner.model.source.HttpSource;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

public class HttpSourceTests {

    @Test
    public void canExecutePresentation()  {
        //Array
        HttpSource httpSource = new HttpSource("name","desc","vk.com", new LinkedList<String>(){{this.add("val1"); this.add("val2");}});
        httpSource.addParameterValue("val1", "test1");
        httpSource.addParameterValue("val2", "test2");

        //Act
        String urlWithParameterValues = httpSource.getUrlWithParameterValues();

        //Assert
        Assert.assertEquals("vk.com?val2=test2&val1=test1", urlWithParameterValues);

    }
}
