package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionState;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.tree.TreeOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ActionTreeController {

    private TreeOrchestrator orchestrator;

    @Autowired
    public ActionTreeController(TreeOrchestrator orchestrator){
        this.orchestrator=orchestrator;
    }

    @PostMapping(path = "/action", consumes = "application/json", produces = "application/json")
    public String execute(@RequestBody ActionArgs actionArgs){
        //ActionArgs actionArgs = new ActionArgs(state);


        return orchestrator.execute(actionArgs).getRawData();
    }

}
