package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionArgsBuilder;
import edu.nc.travelplanner.model.action.ActionState;
import edu.nc.travelplanner.model.tree.TreeOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ActionTreeController {

    private TreeOrchestrator orchestrator;

    @Autowired
    public ActionTreeController(TreeOrchestrator orchestrator){
        this.orchestrator=orchestrator;
    }

    @PostMapping(path = "/action")
    public ResponseEntity<String> execute(@RequestBody ActionArgs actionArgs){
        //ActionArgs actionArgs = new ActionArgs(state);

        return new ResponseEntity<>(orchestrator.execute(actionArgs).getRawData(), HttpStatus.OK) ;
    }

    @GetMapping(path="/get")
    public ActionArgs get(){
        return new ActionArgsBuilder()
                .setState(ActionState.PRESENTATION)
                .addArg("key", "value")
                .build();
    }

}
