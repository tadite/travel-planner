package edu.nc.travelplanner;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.source.CheckListIntegrationAction;
import edu.nc.travelplanner.model.action.source.DropDownListIntegrationAction;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.TextResponse;
import edu.nc.travelplanner.model.source.*;
import edu.nc.travelplanner.model.source.factory.SenderFactory;
import edu.nc.travelplanner.model.source.filter.JsonPathResponseFilter;
import edu.nc.travelplanner.model.source.filter.ListToMapJsonResponseFilter;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DropDownIntegrationTests {


    @Test
    public void canExecutePresentation() throws IOException {
        //Array
        String vkCountriesResponse = "{\"response\":[{\"cid\":19,\"title\":\"Австралия\"},{\"cid\":20,\"title\":\"Австрия\"}]}";
        String actionResponseExpected = "[{\"id\":\"testAction1-name-dropdown-list\",\"type\":\"dropdown_list\",\"data\":{\"19\":\"Австралия\",\"20\":\"Австрия\"}}]";

        HttpSender mockHttpSender = mock(HttpSender.class);
        when(mockHttpSender.send(any(HttpSource.class))).thenReturn(new TextResponse(vkCountriesResponse));

        SenderFactory mockSenderFactory = mock(SenderFactory.class);
        when(mockSenderFactory.createSender(SourceType.HTTP)).thenReturn(mockHttpSender);

        Source mockSource = new HttpSource("vk-countries", "", "");

        List<ResponseFilter> responseFilters = new LinkedList<>();
        responseFilters.add(new JsonPathResponseFilter("$.response[*]"));
        responseFilters.add(new ListToMapJsonResponseFilter("cid", "title"));

        DefaultDataProducer dataProducer = new DefaultDataProducer(mockSenderFactory, mockSource, responseFilters);

        DropDownListIntegrationAction dropDownListIntegrationAction = new DropDownListIntegrationAction("testAction1-name", dataProducer);

        //Act
        Response response = dropDownListIntegrationAction.executePresentation(new ActionArgs());

        //Assert
        String actionResponse = response.getRawData();

        Assert.assertEquals(actionResponseExpected, actionResponse);
    }
}
