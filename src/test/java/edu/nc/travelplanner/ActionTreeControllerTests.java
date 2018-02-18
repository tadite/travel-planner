package edu.nc.travelplanner;


import edu.nc.travelplanner.controller.ActionTreeController;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.action.constant.CheckListAction;
import edu.nc.travelplanner.model.action.constant.DropDownListAction;
import edu.nc.travelplanner.model.action.constant.InfoAction;
import edu.nc.travelplanner.model.action.constant.TextInputAction;
import edu.nc.travelplanner.model.factory.tree.ActionTreeFactory;
import edu.nc.travelplanner.model.factory.tree.ActionTreeParseException;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.jump.NoConditionJump;
import edu.nc.travelplanner.model.resultsMapper.FromJsonResultsMapper;
import edu.nc.travelplanner.model.resultsMapper.ResultsMapperReader;
import edu.nc.travelplanner.model.tree.SimpleActionTree;
import edu.nc.travelplanner.model.tree.SimpleTreeOrchestrator;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ActionTreeControllerTests {

    //TODO:fix test here
    //@Test
    public void canJumpThroughTreeWithCorrectPresentAndPickResults() throws ActionTreeParseException, JSONException, IOException {

        //Array
        Map<String, String> checkOptions = new HashMap<>();
        checkOptions.put("1","option1-title");
        checkOptions.put("2","option2-title");

        Map<String, String> dropdownOptions = new HashMap<>();
        dropdownOptions.put("1","dropdown1-title");
        dropdownOptions.put("2","dropdown2-title");

        InfoAction testAction1 = new InfoAction("testAction1-name","testAction1-viewName", "testAction1-data");
        CheckListAction testAction2 = new CheckListAction("testAction2-name", "testAction2-viewName", checkOptions);
        TextInputAction testAction3 = new TextInputAction("testAction3-name", "testAction3-viewName","testAction3-startData");
        DropDownListAction testAction4 = new DropDownListAction("testAction4-name","testAction4-viewName", dropdownOptions);

        List<Jump> jumps = new LinkedList<>();

        jumps.add(new NoConditionJump(testAction1,testAction2));
        jumps.add(new NoConditionJump(testAction2,testAction3));
        jumps.add(new NoConditionJump(testAction3,testAction4));

        SimpleActionTree tree = new SimpleActionTree("params-tree", testAction1);
        tree.addAllJumps(jumps);

        //removed because gitlab cs cant find json files
        ActionTreeFactory mockActionTreeFactory = mock(ActionTreeFactory.class);
        when(mockActionTreeFactory.createByName(Mockito.anyString())).thenReturn(tree);

        ResultsMapperReader mockResultsMapperReader = mock(ResultsMapperReader.class);
        when(mockResultsMapperReader.read(Mockito.anyString())).thenReturn(new FromJsonResultsMapper(new HashMap<String, String>(){{
            put("from.cityId","testAction4-name");
            put("from.countryId","testAction4-name");
        }}));

        SimpleTreeOrchestrator treeOrchestrator = new SimpleTreeOrchestrator(mockActionTreeFactory, mockResultsMapperReader);

        ActionTreeController actionTreeController = new ActionTreeController(treeOrchestrator);

        //Act
        ResponseEntity<String> testAction1Present = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> testAction1Decision = actionTreeController.executePost(new HashMap<>());
        ResponseEntity<String> testAction2Present = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> testAction2Decision = actionTreeController.executePost(new HashMap<String, String>(){{put("block1.checkbox1.2", "true"); }});
        ResponseEntity<String> testAction3Present = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> testAction3Decision = actionTreeController.executePost(new HashMap<String, String>(){{put("textbox1.testAction3-name", "testAction3-newData"); }});
        ResponseEntity<String> testAction4Present = actionTreeController.executeGet(new HashMap<>());
        ResponseEntity<String> testAction4Decision = actionTreeController.executePost(new HashMap<String, String>(){{put("dropdownlist1.testAction4-name-dropdown-list.1", "true"); }});
        ResponseEntity<String> testAction5Present = actionTreeController.executeGet(new HashMap<>());

        //Assert

        //TODO: pick results
        //        JSONAssert.assertEquals(actionResponseExpected, actionResponse, false);
        JSONAssert.assertEquals("[{\"id\":\"title1.question\",\"data\":\"testAction1-viewName\",\"type\":\"title\"},{\"id\":\"title2.testAction1-name-title\",\"data\":\"testAction1-data\",\"type\":\"title\"}]", testAction1Present.getBody(), false);
        JSONAssert.assertEquals("[{\"id\":\"title1.question\",\"data\":\"testAction2-viewName\",\"type\":\"title\"},{\"id\":\"block1.checkbox1.1\",\"data\":\"option1-title\",\"type\":\"checkbox\"},{\"id\":\"block1.checkbox2.2\",\"data\":\"option2-title\",\"type\":\"checkbox\"}]", testAction2Present.getBody(), false);
        JSONAssert.assertEquals("[{\"id\":\"title1.question\",\"data\":\"testAction3-viewName\",\"type\":\"title\"},{\"id\":\"textbox1.testAction3-name\",\"data\":\"testAction3-startData\",\"type\":\"text_input\"}]", testAction3Present.getBody(), false);
        JSONAssert.assertEquals("[{\"id\":\"title1.question\",\"data\":\"testAction4-viewName\",\"type\":\"title\"},{\"id\":\"dropdownlist1.testAction4-name\",\"type\":\"dropdown_list\",\"data\":{\"dropdownlist1.dropdown2-id\":\"dropdown2-title\",\"dropdownlist1.dropdown1-id\":\"dropdown1-title\"}}]", testAction4Present.getBody(), false);

        Assert.assertEquals("testAction2-name",tree.getPickResults().get(0).getKey());
        Assert.assertEquals("option1-id",((List<String>)tree.getPickResults().get(0).getValue()).get(0));
        Assert.assertEquals("testAction3-name",tree.getPickResults().get(1).getKey());
        Assert.assertEquals("testAction3-newData",tree.getPickResults().get(1).getValue());
        Assert.assertEquals("testAction4-name",tree.getPickResults().get(2).getKey());
        Assert.assertEquals("dropdown1-id",tree.getPickResults().get(2).getValue());
        /*
        [{"id":"question","data":"testAction1-viewName","type":"title"},{"id":"testAction1-name-title","data":"testAction1-data","type":"title"}]
        [{"id":"question","data":"testAction2-viewName","type":"title"},{"id":"option1-id","data":"option1-title","type":"checkbox"},{"id":"option2-id","data":"option2-title","type":"checkbox"}]
        [{"id":"question","data":"testAction3-viewName","type":"title"},{"id":"testAction3-name-textbox","data":"testAction3-startData","type":"text_input"}]
        [{"id":"question","data":"testAction4-viewName","type":"title"},{"id":"testAction4-name-dropdown-list","type":"dropdown_list","data":{"dropdown2-id":"dropdown2-title","dropdown1-id":"dropdown1-title"}}]
         */
    }
}
