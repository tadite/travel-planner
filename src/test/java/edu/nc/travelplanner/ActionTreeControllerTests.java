package edu.nc.travelplanner;

import edu.nc.travelplanner.controller.ActionTreeController;
import edu.nc.travelplanner.controller.MainController;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionArgsBuilder;
import edu.nc.travelplanner.model.action.ActionState;
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



@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionTreeControllerTests {

    @Autowired
    private TreeOrchestrator treeOrchestrator;

    @Test
    public void canConsumeArgsAndJumpThroughTree(){
        //Array
        ActionTreeController actionTreeController = new ActionTreeController(treeOrchestrator);

        //Act
        ResponseEntity<String> responsePresent1 = actionTreeController.execute(new ActionArgsBuilder().setState(ActionState.PRESENTATION).build());
        ResponseEntity<String> responsePresent2 = actionTreeController.execute(new ActionArgsBuilder().setState(ActionState.PRESENTATION).build());
        ResponseEntity<String> responseDecision1 = actionTreeController.execute(new ActionArgsBuilder().setState(ActionState.DECISION).build());
        ResponseEntity<String> responsePresent3 = actionTreeController.execute(new ActionArgsBuilder().setState(ActionState.PRESENTATION).build());
        ResponseEntity<String> responseDecision2 = actionTreeController.execute(new ActionArgsBuilder().setState(ActionState.DECISION).build());
        ResponseEntity<String> responsePresent4 = actionTreeController.execute(new ActionArgsBuilder().setState(ActionState.PRESENTATION).build());

        //Assert

    }
}
