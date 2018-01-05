package edu.nc.travelplanner;

import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.action.constant.CheckListAction;
import edu.nc.travelplanner.model.action.constant.DropDownListAction;
import edu.nc.travelplanner.model.action.constant.InfoAction;
import edu.nc.travelplanner.model.action.constant.TextInputAction;
import edu.nc.travelplanner.model.factory.action.ActionJsonReader;
import edu.nc.travelplanner.model.factory.action.ActionParseException;
import edu.nc.travelplanner.model.factory.action.JsonActionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class JsonActionFactoryTests {

    @Test
    public void canParseTextActionFromJson() throws ActionParseException, IOException {
        //Array
        String testActionName = "test-action";
        String testActionJson = "{\"name\":\"test-action\",\"type\":\"info\",\"parameters\":{\"data\":\"test-data1\"}}";

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

    @Test
    public void canParseCheckboxActionFromJson() throws IOException, ActionParseException {
        //Array
        String testActionName = "test-action";
        String testActionJson = "{\"name\":\"test-action\",\"type\":\"checklist\",\"parameters\":{\"optionsMap\":{\"key1\":\"value1\",\"key2\":\"value2\"}}}";

        ActionJsonReader mockActionJsonReader = Mockito.mock(ActionJsonReader.class);
        Mockito.when(mockActionJsonReader.getActionJson(testActionName)).thenReturn(testActionJson);
        JsonActionFactory jsonActionFactory = new JsonActionFactory(mockActionJsonReader);

        //Act
        Action action = jsonActionFactory.createAction(testActionName);

        //Assert
        CheckListAction checkListAction = (CheckListAction)action;

        Assert.assertEquals(testActionName, checkListAction.getName());
        Assert.assertEquals(ActionType.CHECKLIST, checkListAction.getType());
        Assert.assertEquals("value1", checkListAction.getOptionsMap().get("key1"));
        Assert.assertEquals("value2", checkListAction.getOptionsMap().get("key2"));
    }

    @Test
    public void canParseInputTextActionFromJson() throws IOException, ActionParseException {
        //Array
        String testActionName = "test-action";
        String testActionJson = "{\"name\":\"test-action\",\"type\":\"text_input\",\"parameters\":{\"data\":\"test-data1\"}}";

        ActionJsonReader mockActionJsonReader = Mockito.mock(ActionJsonReader.class);
        Mockito.when(mockActionJsonReader.getActionJson(testActionName)).thenReturn(testActionJson);
        JsonActionFactory jsonActionFactory = new JsonActionFactory(mockActionJsonReader);

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

        ActionJsonReader mockActionJsonReader = Mockito.mock(ActionJsonReader.class);
        Mockito.when(mockActionJsonReader.getActionJson(testActionName)).thenReturn(testActionJson);
        JsonActionFactory jsonActionFactory = new JsonActionFactory(mockActionJsonReader);

        //Act
        DropDownListAction action = (DropDownListAction)jsonActionFactory.createAction(testActionName);

        //Assert

        Assert.assertEquals(testActionName, action.getName());
        Assert.assertEquals(ActionType.DROPDOWN_INPUT, action.getType());
        Assert.assertEquals("value1", action.getOptionsMap().get("key1"));
        Assert.assertEquals("value2", action.getOptionsMap().get("key2"));
    }

}
