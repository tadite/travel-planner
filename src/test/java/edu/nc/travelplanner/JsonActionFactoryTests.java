package edu.nc.travelplanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionDto;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.InfoAction;
import edu.nc.travelplanner.model.factory.ActionJsonReader;
import edu.nc.travelplanner.model.factory.ActionParseException;
import edu.nc.travelplanner.model.factory.FileActionJsonReader;
import edu.nc.travelplanner.model.factory.JsonActionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonActionFactoryTests {

    @Test
    public void canParseTextActionFromJson() throws ActionParseException, IOException {
        //Array
        String testActionName = "test-action";
        String testActionJson = "{\"name\":\"test-action\",\"type\":\"INFO\",\"parameters\":{\"data\":\"test-data1\"}}";

        ActionJsonReader mockActionJsonReader = Mockito.mock(ActionJsonReader.class);
        Mockito.when(mockActionJsonReader.getActionJson(testActionName)).thenReturn(testActionJson);
        JsonActionFactory jsonActionFactory = new JsonActionFactory(mockActionJsonReader);

        //Act
        Action action = jsonActionFactory.createAction(testActionName);

        //Assert
        InfoAction infoAction = (InfoAction)action;

        Assert.assertEquals(testActionName, infoAction.getName());
        Assert.assertEquals(ActionType.INFO, infoAction.getType());
        Assert.assertEquals("test-data1", infoAction.getData());
    }

    public void testJson() throws JsonProcessingException {
        ActionDto actionDto = new ActionDto();
        actionDto.setName("test-action");
        actionDto.setType(ActionType.INFO);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("test-param1", "test-value1");
        actionDto.setParameters(parameters);

        ObjectMapper mapper = new ObjectMapper();
        String actionDtoJson = mapper.writeValueAsString(actionDto);

        return;
    }

    public void testJsonReaderPath() throws IOException {
        FileActionJsonReader fileActionJsonReader = new FileActionJsonReader();
        String actionJson = fileActionJsonReader.getActionJson("test-action");

        Assert.assertEquals("{\"name\":\"test-action\",\"type\":\"INFO\",\"parameters\":{\"data\":\"test-data1\"}}", actionJson);

    }
}
