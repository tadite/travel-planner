package edu.nc.travelplanner;

import edu.nc.travelplanner.exception.DataProducerSendException;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.source.DropDownListIntegrationAction;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.TextResponse;
import edu.nc.travelplanner.model.source.*;
import edu.nc.travelplanner.model.source.dataproducer.DefaultDataProducer;
import edu.nc.travelplanner.model.source.filter.JsonPathResponseFilter;
import edu.nc.travelplanner.model.source.filter.ListToMapJsonResponseFilter;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DropDownIntegrationTests {


    //@Test
    public void canExecutePresentation() throws IOException, JSONException, DataProducerSendException {
        //Array
        String vkCountriesResponse = "{\"response\":[{\"cid\":19,\"title\":\"Австралия\"},{\"cid\":20,\"title\":\"Австрия\"}]}";
        String actionResponseExpected = "[{\"id\":\"testAction1-name-dropdown-list\",\"type\":\"dropdown_list\",\"data\":{\"19\":\"Австралия\",\"20\":\"Австрия\"}}]";

        HttpSender mockHttpSender = mock(HttpSender.class);
        when(mockHttpSender.send(any(HttpSource.class))).thenReturn(new TextResponse(vkCountriesResponse));

        Source mockSource = new HttpSource("vk-countries", "", "", new LinkedList<>());

        List<ResponseFilter> responseFilters = new LinkedList<>();
        responseFilters.add(new JsonPathResponseFilter("$.response[*]"));
        responseFilters.add(new ListToMapJsonResponseFilter("cid", "title"));

        DefaultDataProducer dataProducer = new DefaultDataProducer(mockHttpSender, mockSource, responseFilters);

        DropDownListIntegrationAction dropDownListIntegrationAction = new DropDownListIntegrationAction("testAction1-name","testAction1-viewName", dataProducer);

        //Act
        Response response = dropDownListIntegrationAction.executePresentation(new ActionArgs(), new LinkedList<>());

        //Assert
        String actionResponse = response.getRawData();

        JSONAssert.assertEquals(actionResponseExpected, actionResponse, false);
    }
}
