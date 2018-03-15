package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.exception.CustomParseException;
import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.action.constant.CheckListAction;
import edu.nc.travelplanner.model.action.source.CheckListIntegrationAction;
import edu.nc.travelplanner.model.action.source.RadioListIntegrationAction;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.response.Response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleActionTree implements ActionTree {

    private String name;
    private Action currentAction;
    private List<Jump> jumps = new LinkedList<>();

    private List<PickResult> pickResults = new LinkedList<>();

    private boolean currentActionExecuted = false;

    public SimpleActionTree() {
    }

    public SimpleActionTree(String name, Action headAction) {
        this.name = name;
        this.currentAction = headAction;
    }

    @Override
    public void addAllJumps(Collection<Jump> jumps) {
        this.jumps.addAll(jumps);
    }

    @Override
    public void addJump(Jump jump) {
        this.jumps.add(jump);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Response executePresentation(ActionArgs args) {

        return currentAction.executePresentation(args, pickResults);
    }

    @Override
    public Response executeDecision(ActionArgs args) {
        currentActionExecuted = true;
        Object result = currentAction.getResult(args.getArgs());
        if (result != null) {
            if (currentAction.getType() == ActionType.DATE_INTERVAL_INPUT) {
                DateInterval dateInterval = (DateInterval) result;
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                pickResults.add(new PickResult(currentAction.getName() + "1", format.format(dateInterval.getStartDate())));
                pickResults.add(new PickResult(currentAction.getName() + "2", format.format(dateInterval.getEndDate())));
            } else if (currentAction.getType() == ActionType.CHECKLIST_INTEGRATION) {
                CheckListIntegrationAction checkListAction = (CheckListIntegrationAction) this.currentAction;
                String value = checkListAction.getOptionsMap().get(result);
                List<String> picks = (List<String>) (result);
                List<String> values = picks.stream().map(key -> String.valueOf(checkListAction.getOptionsMap().get(key))).collect(Collectors.toList());
                pickResults.add(new PickResult(currentAction.getName() + ".Key", picks.get(0)));
                pickResults.add(new PickResult(currentAction.getName(), picks.get(0)));
                pickResults.add(new PickResult(currentAction.getName() + ".Value", values.get(0)));
            } else if (currentAction.getType() == ActionType.RADIOLIST_INTEGRATION) {
                RadioListIntegrationAction checkListAction = (RadioListIntegrationAction) this.currentAction;
                String pick = String.valueOf(result);
                String value = checkListAction.getOptionsMap().get(pick);
                pickResults.add(new PickResult(currentAction.getName() + ".Key", pick));
                pickResults.add(new PickResult(currentAction.getName(), pick));
                pickResults.add(new PickResult(currentAction.getName() + ".Value", value));
            } else
                pickResults.add(new PickResult(currentAction.getName(), result));


        }

        Response response = currentAction.executeDecision(args, pickResults);

        this.jumps.stream()
                .filter(jmp -> jmp.getCurrentAction() == this.currentAction && jmp.canJump(args, pickResults, response))
                .findFirst()
                .ifPresent(this::executeJump);

        return response;
    }

    @Override
    public List<PickResult> getPickResults() {
        return pickResults;
    }

    @Override
    public Boolean isEnded() {

        return currentActionExecuted && !jumps.stream().anyMatch(jmp -> jmp.getCurrentAction() == currentAction);
    }

    private void executeJump(Jump jump) {
        if (jump != null) {
            currentAction = jump.getNextAction();
            currentActionExecuted = false;

        }
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public List<Jump> getJumps() {
        return jumps;
    }
}
