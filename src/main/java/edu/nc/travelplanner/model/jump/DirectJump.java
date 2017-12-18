package edu.nc.travelplanner.model.jump;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.source.Response;

public class DirectJump implements Jump {
    private Action currentAction;
    private Action nextAction;

    public DirectJump(Action currentAction, Action nextAction) {
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
    public boolean canJump(ActionArgs args, Response response) {

        return true;
    }
}
