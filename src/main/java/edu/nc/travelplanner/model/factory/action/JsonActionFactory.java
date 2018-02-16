package edu.nc.travelplanner.model.factory.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.action.constant.*;
import edu.nc.travelplanner.model.factory.EnumMapper;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerFactory;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;
import edu.nc.travelplanner.model.source.dataproducer.DataProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class JsonActionFactory implements ActionFactory {

    private ActionJsonReader actionJsonReader;
    private EnumMapper enumMapper;
    private DataProducerFactory dataProducerFactory;

    public JsonActionFactory(@Autowired ActionJsonReader actionJsonReader,
                             @Autowired EnumMapper enumMapper,
                             @Autowired DataProducerFactory dataProducerFactory) {

        this.actionJsonReader = actionJsonReader;
        this.enumMapper = enumMapper;
        this.dataProducerFactory = dataProducerFactory;
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
            throws NoSuchFieldException, IllegalAccessException, DataProducerParseException {
        Action action = createActionByType(actionDto.getType());

        setFieldsByReflection(actionDto, action);

        return action;
    }

    private void setFieldsByReflection(ActionDto actionDto, Action action)
            throws NoSuchFieldException, IllegalAccessException, DataProducerParseException {
        Class<? extends Action> actionClass = action.getClass();

        setActionName(actionClass, action, actionDto.getName());
        setActionViewName(actionClass, action, actionDto.getViewName());
        setActionType(actionClass, action, actionDto.getType());
        setActionParameters(actionClass ,action, actionDto.getParameters());

        setDataProducerByFactory(actionClass ,action, actionDto.getDataProducerName());
    }

    private void setDataProducerByFactory(Class<? extends Action> actionClass, Action action, String dataProducerName) throws DataProducerParseException, NoSuchFieldException, IllegalAccessException {
        if (dataProducerName==null || dataProducerName.isEmpty())
            return;

        DataProducer dataProducer = dataProducerFactory.createDataProducer(dataProducerName);

        Field fieldToSet = actionClass.getDeclaredField("dataProducer");
        fieldToSet.setAccessible(true);
        fieldToSet.set(action, dataProducer);
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

    private void setActionViewName(Class<? extends Action> actionClass, Action action, String viewName)
            throws NoSuchFieldException, IllegalAccessException {
        Field fieldToSet = actionClass.getDeclaredField("viewName");
        fieldToSet.setAccessible(true);
        fieldToSet.set(action, viewName);
    }

    private Action createActionByType(ActionType type) {
        return enumMapper.create(type);
    }

    private void setActionParameters(Class<? extends Action> actionClass, Action action,
                                     Map<String, Object> parameters)
            throws IllegalAccessException, NoSuchFieldException {
        if (parameters ==null || parameters.isEmpty())
            return;

        for (Map.Entry<String,Object> entry : parameters.entrySet()){
            Field fieldToSet = actionClass.getDeclaredField(entry.getKey());
            fieldToSet.setAccessible(true);
            fieldToSet.set(action, entry.getValue());
        }
    }
}
