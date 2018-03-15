package edu.nc.travelplanner.model.jump;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.Response;

import java.util.List;

public class NoConditionJump implements Jump {
    private Action currentAction;
    private Action nextAction;
    private JumpType type;

    public NoConditionJump() {
    }

    public NoConditionJump(Action currentAction, Action nextAction) {
        this.currentAction = currentAction;
        this.nextAction = nextAction;
    }

    @Override
    public Action getCurrentAction() {
        return currentAction;
    }

    @Override
    public Action getNextAction() {
        return nextAction;
    }

    @Override
    public boolean canJump(ActionArgs args, List<PickResult> pickResults, Response response) {
        return true;
    }

    public JumpType getType() {
        return type;
    }
}
