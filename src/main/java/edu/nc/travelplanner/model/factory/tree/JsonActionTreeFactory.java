package edu.nc.travelplanner.model.factory.tree;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.factory.EnumMapper;
import edu.nc.travelplanner.model.factory.action.ActionFactory;
import edu.nc.travelplanner.model.factory.action.ActionParseException;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.jump.JumpType;
import edu.nc.travelplanner.model.jump.NoConditionJump;
import edu.nc.travelplanner.model.tree.ActionTree;
import edu.nc.travelplanner.model.tree.SimpleActionTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class JsonActionTreeFactory implements ActionTreeFactory {

    private ActionTreeJsonReader actionTreeJsonReader;
    private ActionFactory actionFactory;
    private EnumMapper enumMapper;

    public JsonActionTreeFactory(@Autowired ActionTreeJsonReader actionTreeJsonReader,
                                 @Autowired ActionFactory actionFactory,
                                 @Autowired EnumMapper enumMapper) {
        this.actionTreeJsonReader = actionTreeJsonReader;
        this.actionFactory = actionFactory;
        this.enumMapper = enumMapper;
    }

    @Override
    public ActionTree createByName(String name) throws ActionTreeParseException {
        try {
            String actionTreeJson = actionTreeJsonReader.getActionTreeJson(name);

            ObjectMapper objectMapper = new ObjectMapper();
            ActionTreeDto actionTreeDto = objectMapper.readValue(actionTreeJson, ActionTreeDto.class);

            return createActionTreeFromDto(actionTreeDto);

        } catch (Exception e) {
            throw new ActionTreeParseException(name, e);
        }
    }

    private ActionTree createActionTreeFromDto(ActionTreeDto actionTreeDto)
            throws ActionParseException, NoSuchFieldException, IllegalAccessException {

        Map<String, Action> actionMap = createActionMapFromJsonJumps(actionTreeDto);

        List<Jump> jumps = createJumps(actionTreeDto, actionMap);

        return createSimpleActionTree(actionTreeDto.getName(), actionMap.get(actionTreeDto.getHeadActionName()), jumps);
    }

    private List<Jump> createJumps(ActionTreeDto actionTreeDto, Map<String, Action> actionMap)
            throws NoSuchFieldException, IllegalAccessException {
        List<Jump> jumps =new LinkedList<>();

        for (JumpDto jumpDto : actionTreeDto.getJumps()){
            Jump jump = createJumpFromDto(jumpDto, actionMap);
            jumps.add(jump);
        }
        return jumps;
    }

    private SimpleActionTree createSimpleActionTree(String name, Action headAction, List<Jump> jumps)
            throws NoSuchFieldException, IllegalAccessException {
        SimpleActionTree simpleActionTree = new SimpleActionTree();
        Class<? extends SimpleActionTree> actionTreeClass = simpleActionTree.getClass();

        setActionTreeName(actionTreeClass, simpleActionTree, name);
        setActionTreeHeadAction(actionTreeClass, simpleActionTree, headAction);
        setActionTreeJumps(actionTreeClass, simpleActionTree, jumps);

        return simpleActionTree;
    }

    private void setActionTreeJumps(Class<? extends SimpleActionTree> actionTreeClass,
                                    SimpleActionTree simpleActionTree, List<Jump> jumps)
            throws NoSuchFieldException, IllegalAccessException {
        setField(actionTreeClass, simpleActionTree, "jumps", jumps);
    }

    private void setActionTreeHeadAction(Class<? extends SimpleActionTree> actionTreeClass,
                                         SimpleActionTree simpleActionTree, Action headAction)
            throws NoSuchFieldException, IllegalAccessException {
        setField(actionTreeClass, simpleActionTree, "currentAction", headAction);
    }

    private void setActionTreeName(Class<? extends SimpleActionTree> actionTreeClass,
                                   SimpleActionTree simpleActionTree, String name)
            throws NoSuchFieldException, IllegalAccessException {
        setField(actionTreeClass, simpleActionTree, "name", name);
    }

    private Jump createJumpFromDto(JumpDto jumpDto, Map<String, Action> actionMap)
            throws NoSuchFieldException, IllegalAccessException {
        Jump jump = createJumpByType(jumpDto.getType());

        Class<? extends Jump> jumpClass = jump.getClass();
        setJumpType(jumpClass, jump, jumpDto.getType());
        setJumpCurrentAction(jumpClass, jump, actionMap.get(jumpDto.getFromActionName()));
        setJumpNextAction(jumpClass, jump, actionMap.get(jumpDto.getToActionName()));

        return jump;
    }

    private void setJumpNextAction(Class<? extends Jump> jumpClass, Jump jump, Action action)
            throws NoSuchFieldException, IllegalAccessException {
        setField(jumpClass, jump, "nextAction",action);
    }

    private void setJumpCurrentAction(Class<? extends Jump> jumpClass, Jump jump, Action action)
            throws NoSuchFieldException, IllegalAccessException {
        setField(jumpClass, jump, "currentAction",action);
    }

    private void setJumpType(Class<? extends Jump> jumpClass, Jump jump, JumpType type)
            throws NoSuchFieldException, IllegalAccessException {
        setField(jumpClass, jump, "type",type);
    }

    private void setField(Class<?> objClass, Object obj, String name, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field fieldToSet = objClass.getDeclaredField(name);
        fieldToSet.setAccessible(true);
        fieldToSet.set(obj, value);
    }

    private Jump createJumpByType(JumpType type) {
        return enumMapper.create(type);
    }

    private Map<String, Action> createActionMapFromJsonJumps(ActionTreeDto actionTreeDto)
            throws ActionParseException {
        Map<String, Action> actionMap = new HashMap<>();

        Set<String> actionNames = new HashSet<>();
        actionTreeDto.getJumps().stream().forEach(jumpDto -> {
            actionNames.add(jumpDto.getFromActionName());
            actionNames.add(jumpDto.getToActionName());
        });

        for (String actionName : actionNames){
            actionMap.put(actionName, actionFactory.createAction(actionName));
        }

        return actionMap;
    }
}
