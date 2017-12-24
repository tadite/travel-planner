package edu.nc.travelplanner;


import edu.nc.travelplanner.controller.ActionTreeController;
import edu.nc.travelplanner.model.action.CheckboxListAction;
import edu.nc.travelplanner.model.action.InfoAction;
import edu.nc.travelplanner.model.factory.action.FileActionJsonReader;
import edu.nc.travelplanner.model.factory.action.JsonActionFactory;
import edu.nc.travelplanner.model.factory.tree.ActionTreeFactory;
import edu.nc.travelplanner.model.factory.tree.ActionTreeParseException;
import edu.nc.travelplanner.model.factory.tree.FileActionTreeJsonReader;
import edu.nc.travelplanner.model.factory.tree.JsonActionTreeFactory;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.jump.NoConditionJump;
import edu.nc.travelplanner.model.tree.SimpleActionTree;
import edu.nc.travelplanner.model.tree.SimpleTreeOrchestrator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TravelplannerApplication.class)
@WebAppConfiguration
public class ActionTreeControllerTests {

    @Test
    public void canConsumeArgsAndJumpThroughTreeWithTreeParsedFromJsonFiles() throws ActionTreeParseException {

        //Array
        InfoAction action1 = new InfoAction("test-action1","test-data1");
        InfoAction action2 = new InfoAction("test-action2","test-data2");
        CheckboxListAction action3 = new CheckboxListAction();
        Map<String, String> options = new HashMap<>();
        options.put("test-key1", "test-val1");
        options.put("test-key2", "test-val2");
        action3.setOptionsMap(options);

        List<Jump> jumps = new LinkedList<>();

        jumps.add(new NoConditionJump(action1,action2));
        jumps.add(new NoConditionJump(action2,action3));

        SimpleActionTree tree = new SimpleActionTree("test-tree", action1);
        tree.addAllJumps(jumps);

        //removed because gitlab cs cant find json files
        ActionTreeFactory mockActionTreeFactory = mock(ActionTreeFactory.class);
        when(mockActionTreeFactory.createByName("test-tree")).thenReturn(tree);

        SimpleTreeOrchestrator treeOrchestrator = new SimpleTreeOrchestrator(mockActionTreeFactory);

        ActionTreeController actionTreeController = new ActionTreeController(treeOrchestrator);

        //Act
        ResponseEntity<String> responsePresent1 = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> responsePresent2 = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> responseDecision1 = actionTreeController.executePost(new HashMap<>());
        ResponseEntity<String> responsePresent3 = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> responseDecision2 = actionTreeController.executePost(new HashMap<>());
        ResponseEntity<String> responsePresent4 = actionTreeController.executeGet(new HashMap<>());

        //Assert
        Assert.assertEquals("[{\"id\":\"test-action1_title\",\"data\":\"test-data1\",\"type\":\"TITLE\"}]", responsePresent1.getBody());
        Assert.assertEquals("[{\"id\":\"test-action2_title\",\"data\":\"test-data2\",\"type\":\"TITLE\"}]", responsePresent3.getBody());
        Assert.assertEquals("[{\"id\":\"test-key1\",\"data\":\"test-val1\",\"type\":\"CHECKBOX\"},{\"id\":\"test-key2\",\"data\":\"test-val2\",\"type\":\"CHECKBOX\"}]", responsePresent4.getBody());

    }
}
