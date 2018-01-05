package edu.nc.travelplanner;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionArgsBuilder;
import edu.nc.travelplanner.model.action.source.CheckListIntegrationAction;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.TextResponse;
import edu.nc.travelplanner.model.source.*;
import edu.nc.travelplanner.model.source.factory.DefaultSenderFactory;
import edu.nc.travelplanner.model.source.factory.SenderFactory;
import edu.nc.travelplanner.model.source.filter.JsonPathResponseFilter;
import edu.nc.travelplanner.model.source.filter.ListToMapJsonResponseFilter;
import edu.nc.travelplanner.model.source.filter.RegexpReplaceAllResponseFilter;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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

        SenderFactory mockSenderFactory = mock(SenderFactory.class);
        when(mockSenderFactory.createSender(SourceType.HTTP)).thenReturn(mockHttpSender);

        Source mockSource = new HttpSource("vk-countries", "", "");

        List<ResponseFilter> responseFilters = new LinkedList<>();
        responseFilters.add(new JsonPathResponseFilter("$.response[*]"));
        responseFilters.add(new ListToMapJsonResponseFilter("cid", "title"));

        DefaultDataProducer dataProducer = new DefaultDataProducer(mockSenderFactory, mockSource, responseFilters);

        CheckListIntegrationAction checkListIntegrationAction = new CheckListIntegrationAction("testAction1-name", dataProducer);

        //Act
        Response response = checkListIntegrationAction.executePresentation(new ActionArgs());

        //Assert
        String actionResponse = response.getRawData();

        JSONAssert.assertEquals(actionResponseExpected, actionResponse, false);
    }
}
