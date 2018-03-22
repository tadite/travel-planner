package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.exception.DataProducerSendException;
import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.response.ErrorResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;

import java.util.*;

public class SimpleActionTree implements ActionTree {

    private String name;
    private Action currentAction;
    private List<Jump> jumps = new LinkedList<>();

    private List<PickResult> pickResults = new LinkedList<>();

    private boolean currentActionExecuted = false;

    private Integer attemptsCount;
    private Integer waitAfterFail;

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
        int triesLeft = attemptsCount;

        while (triesLeft > 0) {
            try {
                return currentAction.executePresentation(args, pickResults);
            } catch (Exception e) {
                e.printStackTrace();
                triesLeft--;
                try {
                    Thread.sleep(waitAfterFail);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return new ViewResponseBuilder().addTitleElement("question", currentAction.getViewName()).addTitleElement("errorResult", "Данные по запросу не найдены.").build();
    }

    @Override
    public Response executeDecision(ActionArgs args) {
        currentActionExecuted = true;
        currentAction.getResult(args.getArgs(), pickResults);

        Response response = currentAction.executeDecision(args, pickResults);

        executeJumps(args, response);

        while (currentAction.getType() == ActionType.NO_VIEW_TEXT_INTEGRAION) {
            tryGetResultForNoViewAction(args, pickResults);
            currentAction.getResult(args.getArgs(), pickResults);
            executeJumps(args, response);
        }

        return response;
    }

    private void tryGetResultForNoViewAction(ActionArgs args, List<PickResult> picks) {
        int triesLeft = attemptsCount;

        while (triesLeft > 0) {
            try {
                currentAction.executePresentation(args, picks);
                return;
            } catch (DataProducerSendException e) {
                e.printStackTrace();
                triesLeft--;
                try {
                    Thread.sleep(waitAfterFail);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void executeJumps(ActionArgs args, Response response) {
        this.jumps.stream()
                .filter(jmp -> jmp.getCurrentAction() == this.currentAction && jmp.canJump(args, pickResults, response))
                .findFirst()
                .ifPresent(this::executeJump);
    }

    @Override
    public List<PickResult> getPickResults() {
        return pickResults;
    }

    @Override
    public Boolean isEnded() {

        return currentActionExecuted && !jumps.stream().anyMatch(jmp -> jmp.getCurrentAction() == currentAction);
    }

    @Override
    public void setRequestAttemptParams(Integer attemptsCount, Integer waitAfterFail) {
        this.attemptsCount = attemptsCount;
        this.waitAfterFail = waitAfterFail;
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
