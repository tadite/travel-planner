package edu.nc.travelplanner;

import edu.nc.travelplanner.controller.ActionTreeController;
import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.factory.action.FileActionJsonReader;
import edu.nc.travelplanner.model.factory.action.JsonActionFactory;
import edu.nc.travelplanner.model.factory.tree.ActionTreeFactory;
import edu.nc.travelplanner.model.factory.tree.ActionTreeParseException;
import edu.nc.travelplanner.model.factory.tree.FileActionTreeJsonReader;
import edu.nc.travelplanner.model.factory.tree.JsonActionTreeFactory;
import edu.nc.travelplanner.model.jump.NoConditionJump;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.tree.SimpleActionTree;
import edu.nc.travelplanner.model.tree.SimpleTreeOrchestrator;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import javax.lang.model.util.SimpleElementVisitor6;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ActionTreeControllerTests {

    @Test
    public void canConsumeArgsAndJumpThroughTreeWithTreeParsedFromJsonFiles() throws ActionTreeParseException {
        //Array
        InfoAction action1 = new InfoAction("test-action1","test-data1");
        InfoAction action2 = new InfoAction("test-action2","test-data2");
        InfoAction action3 = new InfoAction("test-action3","test-data3");

        List<Jump> jumps = new LinkedList<>();
        jumps.add(new NoConditionJump(action1,action2));
        jumps.add(new NoConditionJump(action2,action3));

        SimpleActionTree tree = new SimpleActionTree("test-tree", action1);
        tree.addAllJumps(jumps);

        FileActionTreeJsonReader fileActionTreeJsonReader = new FileActionTreeJsonReader();
        JsonActionFactory jsonActionFactory = new JsonActionFactory(new FileActionJsonReader());
        JsonActionTreeFactory jsonActionTreeFactory = new JsonActionTreeFactory(fileActionTreeJsonReader, jsonActionFactory);

        SimpleTreeOrchestrator treeOrchestrator = new SimpleTreeOrchestrator(jsonActionTreeFactory);

        ActionTreeController actionTreeController = new ActionTreeController(treeOrchestrator);

        //Act
        ResponseEntity<String> responsePresent1 = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> responsePresent2 = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> responseDecision1 = actionTreeController.executePost(new HashMap<>());
        ResponseEntity<String> responsePresent3 = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> responseDecision2 = actionTreeController.executePost(new HashMap<>());
        ResponseEntity<String> responsePresent4 = actionTreeController.executeGet(new HashMap<>());

        //Assert
        Assert.assertEquals("test-data1", responsePresent1.getBody());
        Assert.assertEquals("test-data2", responsePresent3.getBody());
        Assert.assertEquals("test-data3", responsePresent4.getBody());

    }
}
