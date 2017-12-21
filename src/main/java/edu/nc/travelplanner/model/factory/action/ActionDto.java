package edu.nc.travelplanner.model.factory.action;

import edu.nc.travelplanner.model.action.ActionType;

import java.util.Map;

public class ActionDto {

    private String name;
    private ActionType type;
    private Map<String, Object> parameters;

    public ActionDto(){}

    public ActionDto(String name, ActionType type, Map<String, Object> parameters) {
        this.name = name;
        this.type = type;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
