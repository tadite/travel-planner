package edu.nc.travelplanner.dao.json;

import edu.nc.travelplanner.model.jump.JumpType;
import edu.nc.travelplanner.model.source.SourceType;

import java.util.Map;

public class JumpApiDto {
    private String from;
    private SourceType to;
    private JumpType type;
    private Map<String, Object> parameters;

    public JumpApiDto() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public SourceType getTo() {
        return to;
    }

    public void setTo(SourceType to) {
        this.to = to;
    }

    public JumpType getType() {
        return type;
    }

    public void setType(JumpType type) {
        this.type = type;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
