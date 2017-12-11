package edu.nc.travelplanner;

import edu.nc.travelplanner.controller.ActionTreeController;
import edu.nc.travelplanner.controller.MainController;
import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.tree.ActionTreeFactory;
import edu.nc.travelplanner.model.tree.SimpleActionTree;
import edu.nc.travelplanner.model.tree.SimpleTreeOrchestrator;
import edu.nc.travelplanner.model.tree.TreeOrchestrator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;




public class ActionTreeControllerTests {

    @Test
    public void canConsumeArgsAndJumpThroughTree(){
        //Array
        InfoAction actionHead = new InfoAction("question1","info1");
        InfoAction actionSecond = new InfoAction("question2","info2");
        InfoAction actionThree = new InfoAction("question3","info3");

        actionHead.addJump(new DirectJump(actionHead,actionSecond));
        actionSecond.addJump(new DirectJump(actionSecond,actionThree));

        SimpleActionTree tree = new SimpleActionTree("test-tree", actionHead);

        ActionTreeFactory mockTreeFactory = Mockito.mock(ActionTreeFactory.class);
        Mockito.when(mockTreeFactory.createByName("test-tree")).thenReturn(tree);

        SimpleTreeOrchestrator treeOrchestrator = new SimpleTreeOrchestrator(mockTreeFactory);

        ActionTreeController actionTreeController = new ActionTreeController(treeOrchestrator);

        //Act
        ResponseEntity<String> responsePresent1 = actionTreeController.execute(new ActionArgsBuilder().setState(ActionState.PRESENTATION).build());
        ResponseEntity<String> responsePresent2 = actionTreeController.execute(new ActionArgsBuilder().setState(ActionState.PRESENTATION).build());
        ResponseEntity<String> responseDecision1 = actionTreeController.execute(new ActionArgsBuilder().setState(ActionState.DECISION).build());
        ResponseEntity<String> responsePresent3 = actionTreeController.execute(new ActionArgsBuilder().setState(ActionState.PRESENTATION).build());
        ResponseEntity<String> responseDecision2 = actionTreeController.execute(new ActionArgsBuilder().setState(ActionState.DECISION).build());
        ResponseEntity<String> responsePresent4 = actionTreeController.execute(new ActionArgsBuilder().setState(ActionState.PRESENTATION).build());

        //Assert
        Assert.assertEquals("info1", responsePresent1.getBody());
        Assert.assertEquals("info2", responsePresent3.getBody());
        Assert.assertEquals("info3", responsePresent4.getBody());

    }
}
