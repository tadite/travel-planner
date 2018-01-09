package edu.nc.travelplanner.model.factory.dataproducer;

import edu.nc.travelplanner.model.source.FilterType;

import java.util.Map;

public class ResponseFilterDto {

    private FilterType type;
    private Map<String, Object> parameters;

    public ResponseFilterDto() {
    }

    public ResponseFilterDto(FilterType type, Map<String, Object> parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    public FilterType getType() {
        return type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
