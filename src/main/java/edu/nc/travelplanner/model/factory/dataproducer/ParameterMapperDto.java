package edu.nc.travelplanner.model.factory.dataproducer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParameterMapperDto {
    @JsonProperty("from")
    private String fromKey;

    @JsonProperty("to")
    private String toKey;

    public ParameterMapperDto() {
    }

    public ParameterMapperDto(String fromKey, String toKey) {
        this.fromKey = fromKey;
        this.toKey = toKey;
    }

    public String getFromKey() {
        return fromKey;
    }

    public void setFromKey(String fromKey) {
        this.fromKey = fromKey;
    }

    public String getToKey() {
        return toKey;
    }

    public void setToKey(String toKey) {
        this.toKey = toKey;
    }
}
