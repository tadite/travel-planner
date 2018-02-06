package edu.nc.travelplanner;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.source.InfoIntegrationAction;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.TextResponse;
import edu.nc.travelplanner.model.source.*;
import edu.nc.travelplanner.model.factory.dataproducer.SenderFactory;
import edu.nc.travelplanner.model.source.dataproducer.DefaultDataProducer;
import edu.nc.travelplanner.model.source.filter.JsonPathResponseFilter;
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

public class InfoIntegrationActionTests {

    //@Test
    public void canExecutePresentation() throws IOException, JSONException {
        //Array
        String currencyResponse = "{\n" +
                "\"timestamp\": 1515142420,\n" +
                "\"source\": \"USD\",\n" +
                "\"quotes\": {\n" +
                "\"USDCAD\": 1.250247,\n" +
                "\"USDCHF\": 0.979213,\n" +
                "\"USDEUR\": 0.830483,\n" +
                "\"USDGBP\": 0.737601,\n" +
                "}\n" +
                "}";
        String actionResponseExpected = "[{\"id\":\"testAction1-name-title\",\"data\":\"0.830483\",\"type\":\"title\"}]";

        HttpSender mockHttpSender = mock(HttpSender.class);
        when(mockHttpSender.send(any(HttpSource.class))).thenReturn(new TextResponse(currencyResponse));

        Source mockSource = new HttpSource("currencies", "", "");

        List<ResponseFilter> responseFilters = new LinkedList<>();
        responseFilters.add(new JsonPathResponseFilter("$.quotes.USDEUR"));

        DefaultDataProducer dataProducer = new DefaultDataProducer(mockHttpSender, mockSource, responseFilters );

        InfoIntegrationAction infoIntegrationAction = new InfoIntegrationAction("testAction1-name", dataProducer);

        //Act
        Response response = infoIntegrationAction.executePresentation(new ActionArgs());

        //Assert
        String actionResponse = response.getRawData();

        JSONAssert.assertEquals(actionResponseExpected, actionResponse, false);
    }
}
