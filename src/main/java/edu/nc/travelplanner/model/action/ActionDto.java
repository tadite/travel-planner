package edu.nc.travelplanner.model.action;

import java.util.Map;

public class ActionDto {

    private String name;
    private ActionType type;
    private Map<String, String> parameters;

    public ActionDto(){}

    public ActionDto(String name, ActionType type, Map<String, String> parameters) {
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

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
