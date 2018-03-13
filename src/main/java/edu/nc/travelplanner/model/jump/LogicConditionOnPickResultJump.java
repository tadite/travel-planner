package edu.nc.travelplanner.model.jump;

import edu.nc.travelplanner.exception.CustomParseException;
import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.response.Response;


public class LogicConditionOnPickResultJump implements Jump {
    private Action currentAction;
    private Action nextAction;
    private JumpType type;

    private String conditionType;
    private String pickResultName;
    private String conditionValue;
    private String pickResultCheck;

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
    public boolean canJump(ActionArgs args, Response response){
        try {
            return resolveLogicPickResult(args, response);
        } catch (CustomParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean resolveLogicPickResult(ActionArgs args, Response response) throws CustomParseException {
        if (pickResultCheck == "value" && !args.getArgs().containsValue(conditionValue))
            return false;
        if (pickResultCheck == "key" && !args.getArgs().containsKey(conditionValue))
            return false;

        String value;

        if (pickResultCheck.equals("key"))
            value = args.getArgs().get(conditionValue);
        else if (pickResultCheck.equals("value"))
            value = args.getArgs().entrySet().stream().filter(entry -> entry.getValue().equals(conditionValue)).findFirst().get().getKey();
        else throw new CustomParseException("Value for jump condition not found");

        return resolveValueOnLogicCondition(value, conditionType);
    }

    private boolean resolveValueOnLogicCondition(String value, String logicOperation) throws CustomParseException {
        if (logicOperation.length() != 2)
            throw new CustomParseException("Logic Operation must be 2 symbols");

        switch (logicOperation.charAt(0)) {
            case '<':
                switch (logicOperation.charAt(1)) {
                    case 'd':
                        return Double.valueOf(value) < Double.valueOf(conditionValue);
                    case 'i':
                        return Integer.valueOf(value) < Integer.valueOf(conditionValue);
                }
            case '>':
                switch (logicOperation.charAt(1)) {
                    case 'd':
                        return Double.valueOf(value) > Double.valueOf(conditionValue);
                    case 'i':
                        return Integer.valueOf(value) > Integer.valueOf(conditionValue);
                }
            case '=':
                switch (logicOperation.charAt(1)) {
                    case 'd':
                        return Double.valueOf(value).equals(Double.valueOf(conditionValue));
                    case 'i':
                        return Integer.valueOf(value).equals(Integer.valueOf(conditionValue));
                    case 's':
                        return String.valueOf(value).equals(String.valueOf(conditionValue));
                    case 'o':
                        return value.equals(conditionValue);
                }
            default:
                return false;
        }
    }
}
