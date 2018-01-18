package edu.nc.travelplanner;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.source.CheckListIntegrationAction;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.TextResponse;
import edu.nc.travelplanner.model.source.*;
import edu.nc.travelplanner.model.factory.dataproducer.SenderFactory;
import edu.nc.travelplanner.model.source.dataproducer.DefaultDataProducer;
import edu.nc.travelplanner.model.source.filter.JsonPathResponseFilter;
import edu.nc.travelplanner.model.source.filter.ListToMapJsonResponseFilter;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TravelplannerApplication.class)
//@WebAppConfiguration
public class CheckListIntegrationActionTests {

    @Test
    public void canExecutePresentation() throws IOException, JSONException {
        //Array

        String vkCountriesResponse = "{\"response\":[{\"cid\":19,\"title\":\"Австралия\"},{\"cid\":20,\"title\":\"Австрия\"}]}";
        String actionResponseExpected = "[{\"id\":\"19\",\"data\":\"Австралия\",\"type\":\"checkbox\"},{\"id\":\"20\",\"data\":\"Австрия\",\"type\":\"checkbox\"}]";

        HttpSender mockHttpSender = mock(HttpSender.class);
        when(mockHttpSender.send(any(HttpSource.class))).thenReturn(new TextResponse(vkCountriesResponse));

        Source mockSource = new HttpSource("vk-countries", "", "");

        List<ResponseFilter> responseFilters = new LinkedList<>();
        responseFilters.add(new JsonPathResponseFilter("$.response[*]"));
        responseFilters.add(new ListToMapJsonResponseFilter("cid", "title"));

        DefaultDataProducer dataProducer = new DefaultDataProducer(mockHttpSender, mockSource, responseFilters);

        CheckListIntegrationAction checkListIntegrationAction = new CheckListIntegrationAction("testAction1-name", dataProducer);

        //Act
        Response response = checkListIntegrationAction.executePresentation(new ActionArgs());

        //Assert
        String actionResponse = response.getRawData();

        JSONAssert.assertEquals(actionResponseExpected, actionResponse, false);
    }
}