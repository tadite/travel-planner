package edu.nc.travelplanner;

import edu.nc.travelplanner.controller.ActionTreeController;
import edu.nc.travelplanner.controller.MainController;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionState;
import edu.nc.travelplanner.model.tree.TreeOrchestrator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

public class ActionTreeControllerTests {

    public void canConsumeArgsAndJumpThroughTree(){
        //Array
        TreeOrchestrator mockOrchestrator = Mockito.mock(TreeOrchestrator.class);
        ActionTreeController actionTreeController = new ActionTreeController(mockOrchestrator);

        //Act
        String response = actionTreeController.execute(new ActionArgs(ActionState.PRESENTATION));

        //Assert

    }
}
