package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.response.Response;

import java.util.*;

public class SimpleActionTree implements ActionTree {

    private String name;
    private Action currentAction;
    private List<Jump> jumps = new LinkedList<>();

    private Map<String, Object> pickResult = new HashMap<>();

    public SimpleActionTree() {
    }

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
        Object result = currentAction.getResult(args.getArgs());
        if (result!=null)
            pickResult.put(currentAction.getName(), result);

        Response response = currentAction.executeDecision(args);

        this.jumps.stream()
                .filter(jmp -> jmp.getCurrentAction()==this.currentAction && jmp.canJump(args, response))
                .findFirst()
                .ifPresent(this::executeJump);

        return response;
    }

    @Override
    public Map<String, Object> getPickResults() {
        return pickResult;
    }

    private void executeJump(Jump jump){
        if (jump!=null)
            currentAction=jump.getNextAction();
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public List<Jump> getJumps() {
        return jumps;
    }
}
