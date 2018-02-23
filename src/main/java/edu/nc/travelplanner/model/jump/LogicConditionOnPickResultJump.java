package edu.nc.travelplanner.model.jump;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.response.Response;

public class LogicConditionOnPickResultJump implements Jump {
    private Action currentAction;
    private Action nextAction;
    private JumpType type;

    private String logicOperation;
    private String pickResultName;
    private String rightValue;

    public LogicConditionOnPickResultJump() {
    }

    public LogicConditionOnPickResultJump(Action currentAction, Action nextAction) {
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

        return resolveLogicPickResult(args, response);
    }

    private boolean resolveLogicPickResult(ActionArgs args, Response response) {
        if (!args.getArgs().containsKey(pickResultName))
            return false;

        String value = args.getArgs().get(pickResultName);
        return resolveValueOnLogicCondition(value, logicOperation);
    }

    private boolean resolveValueOnLogicCondition(String value, String logicOperation) {
        switch (logicOperation){
            case "<":
                return Double.valueOf(value)<Double.valueOf(rightValue);
            case ">":
                return Double.valueOf(value)>Double.valueOf(rightValue);
            case "=d=":
                return Double.valueOf(value).equals(Double.valueOf(rightValue));
            case "=s=":
                return value.equals(rightValue);
            default:
                return false;
        }
    }
}
