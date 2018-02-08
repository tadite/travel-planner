package edu.nc.travelplanner;

import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.action.constant.CheckListAction;
import edu.nc.travelplanner.model.action.constant.DropDownListAction;
import edu.nc.travelplanner.model.action.constant.InfoAction;
import edu.nc.travelplanner.model.action.constant.TextInputAction;
import edu.nc.travelplanner.model.action.source.CheckListIntegrationAction;
import edu.nc.travelplanner.model.action.source.DropDownListIntegrationAction;
import edu.nc.travelplanner.model.factory.DefaultEnumMapper;
import edu.nc.travelplanner.model.factory.EnumMapper;
import edu.nc.travelplanner.model.factory.action.*;
import edu.nc.travelplanner.model.factory.dataproducer.*;
import edu.nc.travelplanner.model.factory.filter.DefaultResponseFilterFactory;
import edu.nc.travelplanner.model.factory.source.JsonSourceFactory;
import edu.nc.travelplanner.model.factory.source.SourceFactory;
import edu.nc.travelplanner.model.factory.source.SourceJsonReader;
import edu.nc.travelplanner.model.source.FilterType;
import edu.nc.travelplanner.model.source.HttpSource;
import edu.nc.travelplanner.model.source.SourceType;
import edu.nc.travelplanner.model.source.dataproducer.DataProducer;
import edu.nc.travelplanner.model.source.dataproducer.DefaultDataProducer;
import edu.nc.travelplanner.model.source.filter.JsonPathResponseFilter;
import edu.nc.travelplanner.model.source.filter.ListToMapJsonResponseFilter;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class JsonActionFactoryTests {

   // @Test
    public void canParseTextActionFromJson() throws ActionParseException, IOException {
        //Array
        String testActionName = "test-action";
        String testActionJson = "{\"name\":\"test-action\",\"type\":\"info\",\"parameters\":{\"data\":\"test-data1\"}}";

        ActionJsonReader mockActionJsonReader = mock(ActionJsonReader.class);
        Mockito.when(mockActionJsonReader.getActionJson(testActionName)).thenReturn(testActionJson);
        JsonActionFactory jsonActionFactory = new JsonActionFactory(mockActionJsonReader, new DefaultEnumMapper(), mock(DataProducerFactory.class));

        //Act
        Action action = jsonActionFactory.createAction(testActionName);

        //Assert
        InfoAction infoAction = (InfoAction)action;

        Assert.assertEquals(testActionName, infoAction.getName());
        Assert.assertEquals(ActionType.INFO, infoAction.getType());
        Assert.assertEquals("test-data1", infoAction.getData());
    }

    //@Test
    public void canParseCheckboxActionFromJson() throws IOException, ActionParseException {
        //Array
        String testActionName = "test-action";
        String testActionJson = "{\"name\":\"test-action\",\"type\":\"checklist\",\"parameters\":{\"optionsMap\":{\"key1\":\"value1\",\"key2\":\"value2\"}}}";

        ActionJsonReader mockActionJsonReader = mock(ActionJsonReader.class);
        Mockito.when(mockActionJsonReader.getActionJson(testActionName)).thenReturn(testActionJson);
        JsonActionFactory jsonActionFactory = new JsonActionFactory(mockActionJsonReader, new DefaultEnumMapper(), mock(DataProducerFactory.class));

        //Act
        Action action = jsonActionFactory.createAction(testActionName);

        //Assert
        CheckListAction checkListAction = (CheckListAction)action;

        Assert.assertEquals(testActionName, checkListAction.getName());
        Assert.assertEquals(ActionType.CHECKLIST, checkListAction.getType());
        Assert.assertEquals("value1", checkListAction.getOptionsMap().get("key1"));
        Assert.assertEquals("value2", checkListAction.getOptionsMap().get("key2"));
    }

    //@Test
    public void canParseInputTextActionFromJson() throws IOException, ActionParseException {
        //Array
        String testActionName = "test-action";
        String testActionJson = "{\"name\":\"test-action\",\"type\":\"text_input\",\"parameters\":{\"data\":\"test-data1\"}}";

        ActionJsonReader mockActionJsonReader = mock(ActionJsonReader.class);
        Mockito.when(mockActionJsonReader.getActionJson(testActionName)).thenReturn(testActionJson);
        JsonActionFactory jsonActionFactory = new JsonActionFactory(mockActionJsonReader, new DefaultEnumMapper(), mock(DataProducerFactory.class));

        //Act
        Action action = jsonActionFactory.createAction(testActionName);

        //Assert
        TextInputAction textInput = (TextInputAction)action;

        Assert.assertEquals(testActionName, textInput.getName());
        Assert.assertEquals(ActionType.TEXT_INPUT, textInput.getType());
        Assert.assertEquals("test-data1", textInput.getData());
    }

    @Test
    public void canParseCheckListActionFromJson() throws IOException, ActionParseException {
        //Array
        String testActionName = "test-action";
        String testActionJson = "{\"name\":\"test-action\",\"type\":\"dropdown_input\",\"parameters\":{\"optionsMap\":{\"key1\":\"value1\",\"key2\":\"value2\"}}}";

        ActionJsonReader mockActionJsonReader = mock(ActionJsonReader.class);
        Mockito.when(mockActionJsonReader.getActionJson(testActionName)).thenReturn(testActionJson);
        JsonActionFactory jsonActionFactory = new JsonActionFactory(mockActionJsonReader, new DefaultEnumMapper(), mock(DataProducerFactory.class));

        //Act
        DropDownListAction action = (DropDownListAction)jsonActionFactory.createAction(testActionName);

        //Assert

        Assert.assertEquals(testActionName, action.getName());
        Assert.assertEquals(ActionType.DROPDOWN_INPUT, action.getType());
        Assert.assertEquals("value1", action.getOptionsMap().get("key1"));
        Assert.assertEquals("value2", action.getOptionsMap().get("key2"));
    }

    //TODO: parsing
    //@Test
    public void canParseCheckListIntegrationActionFromJson() throws IOException, ActionParseException {
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

        ActionType actionType = ActionType.CHECKLIST_INTEGRATION;
        String testActionName = "test-action";
        String testActionJson = "{\"name\":\"test-action\"," +
                "\"type\":\"checklist_integration\"," +
                "\"dataProducer\": \""+dataProducerName+"\"}";

        EnumMapper enumMapper = new DefaultEnumMapper();

        ActionJsonReader mockActionJsonReader = mock(ActionJsonReader.class);
        Mockito.when(mockActionJsonReader.getActionJson(testActionName)).thenReturn(testActionJson);

        DataProducerJsonReader mockDataProducerJsonReader = mock(DataProducerJsonReader.class);
        when(mockDataProducerJsonReader.getDataProducerJson(dataProducerName)).thenReturn(dataProducerJson);

        SourceJsonReader mockSourceJsonReader = mock(SourceJsonReader.class);
        when(mockSourceJsonReader.getSourceJson(sourceName)).thenReturn(sourceJson);

        SourceFactory sourceFactory = new JsonSourceFactory(mockSourceJsonReader, enumMapper);

        SenderFactory senderFactory = new DefaultSenderFactory(enumMapper);

        DataProducerFactory dataProducerFactory = new JsonDataProducerFactory(new DefaultResponseFilterFactory(enumMapper),
                mockDataProducerJsonReader, sourceFactory, senderFactory);

        JsonActionFactory jsonActionFactory = new JsonActionFactory(mockActionJsonReader, enumMapper, dataProducerFactory);

        //Act
        CheckListIntegrationAction action = (CheckListIntegrationAction)jsonActionFactory.createAction(testActionName);

        //Assert
        Assert.assertEquals(testActionName, action.getName());
        Assert.assertEquals(actionType, action.getType());

        DefaultDataProducer dataProducer = (DefaultDataProducer)action.getDataProducer();
        Assert.assertEquals(dataProducerName, dataProducer.getName());

        HttpSource source = (HttpSource)dataProducer.getSource();
        Assert.assertEquals(sourceName, source.getName());
        Assert.assertEquals(sourceType, source.getType());
        Assert.assertEquals(sourceUrl, source.getUrl());
        Assert.assertEquals(sourceDescription, source.getDescription());

        JsonPathResponseFilter jsonPathResponseFilter = (JsonPathResponseFilter)dataProducer.getResponseFilters().get(0);
        Assert.assertEquals(jsonPathFilterType, jsonPathResponseFilter.getType());
        Assert.assertEquals(jsonPathFilterExpression, jsonPathResponseFilter.getExpression());

        ListToMapJsonResponseFilter listToMapJsonResponseFilter = (ListToMapJsonResponseFilter)dataProducer.getResponseFilters().get(1);
        Assert.assertEquals(keyName, listToMapJsonResponseFilter.getKeyName());
        Assert.assertEquals(valueName, listToMapJsonResponseFilter.getValueName());
        Assert.assertEquals(listToMapFilterType, listToMapJsonResponseFilter.getType());


    }


}
