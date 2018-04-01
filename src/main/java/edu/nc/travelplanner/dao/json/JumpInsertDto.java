package edu.nc.travelplanner.dao.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.nc.travelplanner.model.jump.JumpType;

public class JumpInsertDto {
    @JsonProperty("action")
    private String actionName;
    private String from;
    private JumpType jumpType;
    private String to;

    public JumpInsertDto() {
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public JumpType getJumpType() {
        return jumpType;
    }

    public void setJumpType(JumpType jumpType) {
        this.jumpType = jumpType;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
