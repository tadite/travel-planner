package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.exception.DataProducerSendException;
import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.response.ErrorResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class SimpleActionTree implements ActionTree {

    private String name;
    private Action currentAction;
    private List<Jump> jumps = new LinkedList<>();

    private List<PickResult> pickResults = new LinkedList<>();

    private boolean currentActionExecuted = false;

    private Integer attemptsCount;
    private Integer waitAfterFail;

    private RollbackMaster rollbackMaster=new RollbackMaster();

    private boolean failedAtPresent=false;

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
    public void rollback() {
        if (rollbackMaster.isEmpty())
            return;

        HistoryState previousState = rollbackMaster.rollback();
        this.pickResults=previousState.getPicks();
        this.currentAction=previousState.getJastJump().getCurrentAction();
    }

    @Override
    public Response executePresentation(ActionArgs args) {
        int triesLeft = attemptsCount;
        failedAtPresent=false;

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

        failedAtPresent=true;
        return new ViewResponseBuilder().addTitleElement("question", currentAction.getViewName()).addTitleElement("errorResult", "Данные по запросу не найдены.").build();
    }

    @Override
    public Response executeDecision(ActionArgs args) {
        if (failedAtPresent)
        {
            Jump forcedJump = executeJumps(null, null, true);
            if (forcedJump!=null)
                rollbackMaster.addStep(new HistoryState(this.pickResults, forcedJump));
        }

        currentActionExecuted = true;
        Jump currentStepJump = null;
        LinkedList<PickResult> currentStepPicks = new LinkedList<>(this.pickResults);

        currentAction.getResult(args.getArgs(), this.pickResults);
        Response response = currentAction.executeDecision(args, this.pickResults);
        currentStepJump = executeJumps(args, response, false);

        if (currentStepJump!=null)
            rollbackMaster.addStep(new HistoryState(currentStepPicks, currentStepJump));

        while (currentAction.getType() == ActionType.NO_VIEW_TEXT_INTEGRAION) {
            tryGetResultForNoViewAction(args, this.pickResults);
            currentAction.getResult(args.getArgs(), this.pickResults);
            executeJumps(args, response, false);
        }

        return response;
    }

    private void tryGetResultForNoViewAction(ActionArgs args, List<PickResult> picks) {
        int triesLeft = attemptsCount;

        while (triesLeft > 0) {
            try {
                currentAction.executePresentation(args, picks);
                return;
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
    }

    private Jump executeJumps(ActionArgs args, Response response, Boolean force) {
        Optional<Jump> jump = this.jumps.stream()
                .filter(jmp -> jmp.getCurrentAction() == this.currentAction && (force || jmp.canJump(args, pickResults, response)))
                .findFirst();

        if (!jump.isPresent())
            return null;

        Jump jumpToExecute = jump.get();
        executeJump(jumpToExecute);
        return jumpToExecute;
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
