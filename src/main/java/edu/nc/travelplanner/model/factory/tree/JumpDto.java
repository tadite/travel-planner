package edu.nc.travelplanner.model.factory.tree;

import edu.nc.travelplanner.model.jump.JumpType;

import java.util.Map;

public class JumpDto {

    private String fromActionName;
    private String toActionName;
    private JumpType type;
    private Map<String, String> params;

    public JumpDto() {
    }

    public JumpDto(String fromActionName, String toActionName, JumpType type, Map<String, String> params) {
        this.fromActionName = fromActionName;
        this.toActionName = toActionName;
        this.type = type;
        this.params = params;
    }

    public String getToActionName() {
        return toActionName;
    }

    public void setToActionName(String toActionName) {
        this.toActionName = toActionName;
    }

    public JumpType getType() {
        return type;
    }

    public void setType(JumpType type) {
        this.type = type;
    }

    public String getFromActionName() {
        return fromActionName;
    }

    public void setFromActionName(String fromActionName) {
        this.fromActionName = fromActionName;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
