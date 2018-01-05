package edu.nc.travelplanner;


import edu.nc.travelplanner.controller.ActionTreeController;
import edu.nc.travelplanner.model.action.constant.CheckListAction;
import edu.nc.travelplanner.model.action.constant.DropDownListAction;
import edu.nc.travelplanner.model.action.constant.InfoAction;
import edu.nc.travelplanner.model.action.constant.TextInputAction;
import edu.nc.travelplanner.model.factory.tree.ActionTreeFactory;
import edu.nc.travelplanner.model.factory.tree.ActionTreeParseException;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.jump.NoConditionJump;
import edu.nc.travelplanner.model.tree.SimpleActionTree;
import edu.nc.travelplanner.model.tree.SimpleTreeOrchestrator;
import org.junit.Assert;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TravelplannerApplication.class)
//@WebAppConfiguration
public class ActionTreeControllerTests {

    //GITLAB CI CANT RUN TEST
    //@Test
    public void canJumpThroughTreeWithCorrectPresent() throws ActionTreeParseException {

        //Array
        Map<String, String> checkOptions = new HashMap<>();
        checkOptions.put("option1-id","option1-title");
        checkOptions.put("option2-id","option2-title");

        Map<String, String> dropdownOptions = new HashMap<>();
        dropdownOptions.put("dropdown1-id","dropdown1-title");
        dropdownOptions.put("dropdown2-id","dropdown2-title");

        InfoAction testAction1 = new InfoAction("testAction1-name", "testAction1-data");
        CheckListAction testAction2 = new CheckListAction("testAction2-name",checkOptions);
        TextInputAction testAction3 = new TextInputAction("testAction3-name", "testAction3-startData");
        DropDownListAction testAction4 = new DropDownListAction("testAction4-name", dropdownOptions);

        List<Jump> jumps = new LinkedList<>();

        jumps.add(new NoConditionJump(testAction1,testAction2));
        jumps.add(new NoConditionJump(testAction2,testAction3));
        jumps.add(new NoConditionJump(testAction3,testAction4));

        SimpleActionTree tree = new SimpleActionTree("test-tree", testAction1);
        tree.addAllJumps(jumps);

        //removed because gitlab cs cant find json files
        ActionTreeFactory mockActionTreeFactory = mock(ActionTreeFactory.class);
        when(mockActionTreeFactory.createByName("test-tree")).thenReturn(tree);

        SimpleTreeOrchestrator treeOrchestrator = new SimpleTreeOrchestrator(mockActionTreeFactory);

        ActionTreeController actionTreeController = new ActionTreeController(treeOrchestrator);

        //Act
        ResponseEntity<String> testAction1Present = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> testAction1Decision = actionTreeController.executePost(new HashMap<>());
        ResponseEntity<String> testAction2Present = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> testAction2Decision = actionTreeController.executePost(new HashMap<String, String>(){{put("option1-id", "true"); }});
        ResponseEntity<String> testAction3Present = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> testAction3Decision = actionTreeController.executePost(new HashMap<String, String>(){{put("testAction3-name-textbox", "testAction3-newData"); }});
        ResponseEntity<String> testAction4Present = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> testAction4Decision = actionTreeController.executePost(new HashMap<String, String>(){{put("dropdown1-id", "true"); }});
        //Assert

        //TODO: pick results
        Assert.assertEquals("[{\"id\":\"testAction1-name-title\",\"data\":\"testAction1-data\",\"type\":\"title\"}]", testAction1Present.getBody());
        Assert.assertEquals("[{\"id\":\"option1-id\",\"data\":\"option1-title\",\"type\":\"checkbox\"},{\"id\":\"option2-id\",\"data\":\"option2-title\",\"type\":\"checkbox\"}]", testAction2Present.getBody());
        Assert.assertEquals("[{\"id\":\"testAction3-name-textbox\",\"data\":\"testAction3-startData\",\"type\":\"text_input\"}]", testAction3Present.getBody());
        Assert.assertEquals("[{\"id\":\"testAction4-name-dropdown-list\",\"type\":\"dropdown_list\",\"data\":{\"dropdown2-id\":\"dropdown2-title\",\"dropdown1-id\":\"dropdown1-title\"}}]", testAction4Present.getBody());

    }
}
