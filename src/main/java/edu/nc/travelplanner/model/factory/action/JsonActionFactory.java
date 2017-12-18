package edu.nc.travelplanner.model.factory.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.InfoAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Map;

public class JsonActionFactory implements ActionFactory {

    private ActionJsonReader actionJsonReader;

    public JsonActionFactory(@Autowired ActionJsonReader actionJsonReader) {

        this.actionJsonReader = actionJsonReader;
    }

    @Override
    public Action createAction(String name) throws ActionParseException {
        try {
            String actionJson = actionJsonReader.getActionJson(name);

            ObjectMapper objectMapper = new ObjectMapper();
            ActionDto actionDto = objectMapper.readValue(actionJson, ActionDto.class);

            return createActionFromDto(actionDto);

        } catch (Exception e) {
            throw new ActionParseException(name,e);
        }
    }

    private Action createActionFromDto(ActionDto actionDto)
            throws NoSuchFieldException, IllegalAccessException {
        Action action = createActionByType(actionDto.getType());

        setFieldsByReflection(actionDto, action);

        return action;
    }

    private void setFieldsByReflection(ActionDto actionDto, Action action)
            throws NoSuchFieldException, IllegalAccessException {
        Class<? extends Action> actionClass = action.getClass();

        setActionName(actionClass, action, actionDto.getName());
        setActionType(actionClass, action, actionDto.getType());
        setActionParameters(actionClass ,action, actionDto.getParameters());
    }

    private void setActionType(Class<? extends Action> actionClass, Action action, ActionType type)
            throws NoSuchFieldException, IllegalAccessException {
        Field fieldToSet = actionClass.getDeclaredField("type");
        fieldToSet.setAccessible(true);
        fieldToSet.set(action, type);
    }

    private void setActionName(Class<? extends Action> actionClass, Action action, String name)
            throws NoSuchFieldException, IllegalAccessException {
        Field fieldToSet = actionClass.getDeclaredField("name");
        fieldToSet.setAccessible(true);
        fieldToSet.set(action, name);
    }

    private Action createActionByType(ActionType type) {
        switch (type) {
            case INFO:
                return new InfoAction();
        }
        return null;
    }

    private void setActionParameters(Class<? extends Action> actionClass, Action action,
                                     Map<String, String> parameters)
            throws IllegalAccessException, NoSuchFieldException {
        for (Map.Entry<String,String> entry : parameters.entrySet()){
            Field fieldToSet = actionClass.getDeclaredField(entry.getKey());
            fieldToSet.setAccessible(true);
            fieldToSet.set(action, entry.getValue());
        }
    }
}
