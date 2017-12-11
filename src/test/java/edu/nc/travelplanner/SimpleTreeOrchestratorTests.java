package edu.nc.travelplanner;

import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.source.Response;
import edu.nc.travelplanner.model.tree.SimpleActionTree;
import edu.nc.travelplanner.model.tree.SimpleTreeOrchestrator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.LinkedList;
import java.util.List;

public class SimpleTreeOrchestratorTests {
/*
    public void canJumpThroughTree(){
        //Array
        List<Jump> jumpList = new LinkedList<>();
        InfoAction actionHead = new InfoAction("info1-info-node","data-pres-1");
        InfoAction actionSecond = new InfoAction("info2-info-node","data-pres-2");
        InfoAction actionThree = new InfoAction("info3-info-node","data-pres-3");

        actionHead.addJump(new DirectJump(actionHead,actionSecond));
        actionSecond.addJump(new DirectJump(actionSecond,actionThree));

        SimpleActionTree tree = new SimpleActionTree("test-tree",actionHead);
        SimpleTreeOrchestrator orchestrator = new SimpleTreeOrchestrator();

        //Act
        Response responsePresentationFirst = orchestrator.executePresentation(new ActionArgs(ActionState.PRESENTATION));
        Response responseDesicionFirst = orchestrator.executeDecision(new ActionArgs(ActionState.DECISION));
        Response responsePresentationSecond = orchestrator.executePresentation(new ActionArgs(ActionState.PRESENTATION));
        Response responseDesicionSecond = orchestrator.executeDecision(new ActionArgs(ActionState.DECISION));
        Response responsePresentationThird = orchestrator.executePresentation(new ActionArgs(ActionState.PRESENTATION));
        Response responseDesicionThird = orchestrator.executeDecision(new ActionArgs(ActionState.DECISION));

        //Assert
        Assert.assertEquals("data-pres-1",responsePresentationFirst.getRawData());
        Assert.assertEquals("data-pres-2",responsePresentationSecond.getRawData());
        Assert.assertEquals("data-pres-3",responsePresentationThird.getRawData());
    }*/
}
