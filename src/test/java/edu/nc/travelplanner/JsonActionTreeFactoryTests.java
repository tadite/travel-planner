package edu.nc.travelplanner;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.constant.InfoAction;
import edu.nc.travelplanner.model.factory.DefaultEnumMapper;
import edu.nc.travelplanner.model.factory.action.ActionFactory;
import edu.nc.travelplanner.model.factory.action.ActionParseException;
import edu.nc.travelplanner.model.factory.tree.*;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.tree.SimpleActionTree;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

public class JsonActionTreeFactoryTests {

    @Test
    public void canParseActionTreeFromJson() throws IOException, ActionParseException, ActionTreeParseException {
        //Array
        String testActionName = "test-tree";
        String actionTreeJson = "{\"name\":\"test-tree\",\"headActionName\":\"test-action-1\",\"jumps\":[{\"fromActionName\":\"test-action-1\",\"toActionName\":\"test-action-2\",\"type\":\"without-condition\",\"params\":{\"test-key\":\"test-value\"}},{\"fromActionName\":\"test-action-2\",\"toActionName\":\"test-action-3\",\"type\":\"without-condition\",\"params\":{\"test-key\":\"test-value\"}}]}";

        ActionTreeJsonReader mockActionJsonReader = Mockito.mock(ActionTreeJsonReader.class);
        Mockito.when(mockActionJsonReader.getActionTreeJson(testActionName)).thenReturn(actionTreeJson);

        Action testAction1 = new InfoAction();
        Action testAction2 = new InfoAction();
        Action testAction3 = new InfoAction();

        ActionFactory mockActionFactory = Mockito.mock(ActionFactory.class);
        Mockito.when(mockActionFactory.createAction("test-action-1")).thenReturn(testAction1);
        Mockito.when(mockActionFactory.createAction("test-action-2")).thenReturn(testAction2);
        Mockito.when(mockActionFactory.createAction("test-action-3")).thenReturn(testAction3);

        JsonActionTreeFactory jsonActionTreeFactory = new JsonActionTreeFactory(mockActionJsonReader, mockActionFactory, new DefaultEnumMapper());

        //Act
        SimpleActionTree testActionTree = (SimpleActionTree)jsonActionTreeFactory.createByName(testActionName);

        //Assert
        List<Jump> jumps = testActionTree.getJumps();
        Assert.assertEquals(testActionName, testActionTree.getName());
        Assert.assertEquals(testAction1, testActionTree.getCurrentAction());
        Assert.assertEquals(testAction1, jumps.get(0).getCurrentAction());
        Assert.assertEquals(testAction2, jumps.get(0).getNextAction());
        Assert.assertEquals(testAction2, jumps.get(1).getCurrentAction());
        Assert.assertEquals(testAction3, jumps.get(1).getNextAction());
    }

}