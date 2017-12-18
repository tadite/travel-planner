package edu.nc.travelplanner;

import edu.nc.travelplanner.controller.ActionTreeController;
import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.factory.tree.ActionTreeFactory;
import edu.nc.travelplanner.model.factory.tree.ActionTreeParseException;
import edu.nc.travelplanner.model.jump.NoConditionJump;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.tree.SimpleActionTree;
import edu.nc.travelplanner.model.tree.SimpleTreeOrchestrator;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ActionTreeControllerTests {

    @Test
    public void canConsumeArgsAndJumpThroughTree() throws ActionTreeParseException {
        //Array
        InfoAction actionHead = new InfoAction("question1","info1");
        InfoAction actionSecond = new InfoAction("question2","info2");
        InfoAction actionThree = new InfoAction("question3","info3");

        List<Jump> jumps = new LinkedList<>();
        jumps.add(new NoConditionJump(actionHead,actionSecond));
        jumps.add(new NoConditionJump(actionSecond,actionThree));

        SimpleActionTree tree = new SimpleActionTree("test-tree", actionHead);
        tree.addAllJumps(jumps);

        ActionTreeFactory mockTreeFactory = Mockito.mock(ActionTreeFactory.class);
        Mockito.when(mockTreeFactory.createByName("test-tree")).thenReturn(tree);

        SimpleTreeOrchestrator treeOrchestrator = new SimpleTreeOrchestrator(mockTreeFactory);

        ActionTreeController actionTreeController = new ActionTreeController(treeOrchestrator);

        //Act
        ResponseEntity<String> responsePresent1 = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> responsePresent2 = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> responseDecision1 = actionTreeController.executePost(new HashMap<>());
        ResponseEntity<String> responsePresent3 = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> responseDecision2 = actionTreeController.executePost(new HashMap<>());
        ResponseEntity<String> responsePresent4 = actionTreeController.executeGet(new HashMap<>());

        //Assert
        Assert.assertEquals("info1", responsePresent1.getBody());
        Assert.assertEquals("info2", responsePresent3.getBody());
        Assert.assertEquals("info3", responsePresent4.getBody());

    }
}
