package edu.nc.travelplanner.model.jump;

import edu.nc.travelplanner.exception.CustomParseException;
import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.Response;

import java.util.List;


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
    public boolean canJump(ActionArgs args, List<PickResult> pickResults, Response response){
        try {
            return resolveLogicPickResult(pickResults, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean resolveLogicPickResult(List<PickResult> pickResults, Response response) throws CustomParseException {
        if (!((pickResultCheck.equals("value") && pickResults.stream().anyMatch(pick -> pick.getValue().equals(pickResultName)))||
                (pickResultCheck.equals("key") && pickResults.stream().anyMatch(pick -> pick.getKey().equals(pickResultName)))))
            return false;

        Object value;

        if (pickResultCheck.equals("key"))
            value = pickResults.stream().filter(pick -> pick.getKey().equals(pickResultName)).findFirst().get().getValue();
        else if (pickResultCheck.equals("value"))
            value = pickResults.stream().filter(pick -> pick.getValue().equals(pickResultName)).findFirst().get().getKey();
        else throw new CustomParseException("Value for jump condition not found");

        return resolveValueOnLogicCondition(value, conditionType);
    }

    private boolean resolveLogicPickResult(ActionArgs args, Response response) throws CustomParseException {
        if (!((pickResultCheck == "value" && args.getArgs().containsValue(pickResultName))||
                (pickResultCheck == "key" && args.getArgs().containsKey(pickResultName))))
            return false;

        String value;

        if (pickResultCheck.equals("key"))
            value = args.getArgs().get(pickResultName);
        else if (pickResultCheck.equals("value"))
            value = args.getArgs().entrySet().stream().filter(entry -> entry.getValue().equals(pickResultName)).findFirst().get().getKey();
        else throw new CustomParseException("Value for jump condition not found");

        return resolveValueOnLogicCondition(value, conditionType);
    }

    private boolean resolveValueOnLogicCondition(Object value, String logicOperation) throws CustomParseException {
        if (logicOperation.length() != 2)
            throw new CustomParseException("Logic Operation must be 2 symbols");

        switch (logicOperation.charAt(0)) {
            case '<':
                switch (logicOperation.charAt(1)) {
                    case 'd':
                        return (Double)(value) < Double.valueOf(conditionValue);
                    case 'i':
                        return (Integer)(value) < Integer.valueOf(conditionValue);
                }
            case '>':
                switch (logicOperation.charAt(1)) {
                    case 'd':
                        return (Double)(value) > Double.valueOf(conditionValue);
                    case 'i':
                        return (Integer)(value) > Integer.valueOf(conditionValue);
                }
            case '=':
                switch (logicOperation.charAt(1)) {
                    case 'd':
                        return value.equals(Double.valueOf(conditionValue));
                    case 'i':
                        return value.equals(Integer.valueOf(conditionValue));
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
