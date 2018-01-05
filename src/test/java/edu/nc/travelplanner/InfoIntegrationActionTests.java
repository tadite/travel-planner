package edu.nc.travelplanner;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.source.CheckListIntegrationAction;
import edu.nc.travelplanner.model.action.source.InfoIntegrationAction;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.TextResponse;
import edu.nc.travelplanner.model.source.*;
import edu.nc.travelplanner.model.source.factory.SenderFactory;
import edu.nc.travelplanner.model.source.filter.JsonPathResponseFilter;
import edu.nc.travelplanner.model.source.filter.ListToMapJsonResponseFilter;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import edu.nc.travelplanner.model.source.filter.SubstringResponseFilter;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InfoIntegrationActionTests {

    @Test
    public void canExecutePresentation() throws IOException {
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

        SenderFactory mockSenderFactory = mock(SenderFactory.class);
        when(mockSenderFactory.createSender(SourceType.HTTP)).thenReturn(mockHttpSender);

        Source mockSource = new HttpSource("currencies", "", "");

        List<ResponseFilter> responseFilters = new LinkedList<>();
        responseFilters.add(new JsonPathResponseFilter("$.quotes.USDEUR"));

        DefaultDataProducer dataProducer = new DefaultDataProducer(mockSenderFactory, mockSource, responseFilters);

        InfoIntegrationAction infoIntegrationAction = new InfoIntegrationAction("testAction1-name", dataProducer);

        //Act
        Response response = infoIntegrationAction.executePresentation(new ActionArgs());

        //Assert
        String actionResponse = response.getRawData();

        Assert.assertEquals(actionResponseExpected, actionResponse);
    }
}
