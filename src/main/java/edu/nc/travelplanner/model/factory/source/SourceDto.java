package edu.nc.travelplanner.model.factory.source;

import edu.nc.travelplanner.model.source.SourceType;

import java.util.Map;

public class SourceDto {
    private String name;
    private SourceType type;
    private Map<String, Object> parameters;

    public SourceDto() {
    }

    public SourceDto(String name, SourceType type, Map<String, Object> parameters) {
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

    public SourceType getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type = type;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
