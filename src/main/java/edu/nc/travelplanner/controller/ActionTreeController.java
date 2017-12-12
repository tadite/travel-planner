package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionArgsBuilder;
import edu.nc.travelplanner.model.action.ActionState;
import edu.nc.travelplanner.model.tree.TreeOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class ActionTreeController {

    private TreeOrchestrator orchestrator;

    @Autowired
    public ActionTreeController(TreeOrchestrator orchestrator){

        this.orchestrator=orchestrator;
    }

    @PostMapping(path = "/action")
    public ResponseEntity<String> executePost(@RequestBody Map<String, String> argsMap){

        ActionArgs actionArgs = new ActionArgsBuilder()
                .addAllArgs(argsMap)
                .setState(ActionState.DECISION)
                .build();

        return new ResponseEntity<>(orchestrator.execute(actionArgs).getRawData(), HttpStatus.OK) ;
    }

    @GetMapping(path = "/action")
    public ResponseEntity<String> executeGet(@RequestParam Map<String, String> argsMap){

        ActionArgs actionArgs = new ActionArgsBuilder()
                .addAllArgs(argsMap)
                .setState(ActionState.PRESENTATION)
                .build();

        return new ResponseEntity<>(orchestrator.execute(actionArgs).getRawData(), HttpStatus.OK) ;
    }

    //For testing purpose
    @GetMapping(path="/get")
    public ActionArgs get(){
        return new ActionArgsBuilder()
                .setState(ActionState.PRESENTATION)
                .addArg("key", "value")
                .build();
    }

}
