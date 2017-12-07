package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.Jump;
import edu.nc.travelplanner.model.source.Response;

public class SimpleActionTree implements ActionTree {

    private String name;
    private Action action;

    public SimpleActionTree(String name, Action action) {
        this.name = name;
        this.action = action;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Response executePresentation() {
        return action.executePresentation();
    }

    @Override
    public Response executeDecision(ActionArgs args) {
        Response response = action.executeDecision(args);

        Jump jump = action.getJumps()
                .stream()
                .filter(jmp -> jmp.canJump(args, response))
                .findFirst()
                .get();

        executeJump(jump);

        return response;
    }

    private void executeJump(Jump jump){
        if (jump!=null)
            this.action=jump.getNext();
    }
}
