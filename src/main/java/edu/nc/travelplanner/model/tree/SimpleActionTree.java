package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.source.Response;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SimpleActionTree implements ActionTree {

    private String name;
    private Action currentAction;
    private List<Jump> jumps = new LinkedList<>();

    public SimpleActionTree(String name, Action headAction) {
        this.name = name;
        this.currentAction = headAction;
    }

    @Override
    public void addAllJumps(Collection<Jump> jumps){
        this.jumps.addAll(jumps);
    }

    @Override
    public void addJump(Jump jump){
        this.jumps.add(jump);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Response executePresentation(ActionArgs args) {

        return currentAction.executePresentation(args);
    }

    @Override
    public Response executeDecision(ActionArgs args) {
        Response response = currentAction.executeDecision(args);

        this.jumps.stream()
                .filter(jmp -> jmp.getCurrentAction()==this.currentAction && jmp.canJump(args, response))
                .findFirst()
                .ifPresent(this::executeJump);

        return response;
    }

    private void executeJump(Jump jump){
        if (jump!=null)
            currentAction=jump.getNextAction();
    }
}
