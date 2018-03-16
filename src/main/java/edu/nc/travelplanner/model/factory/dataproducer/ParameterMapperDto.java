package edu.nc.travelplanner.model.factory.dataproducer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParameterMapperDto {
    @JsonProperty("from")
    private String fromKey;

    @JsonProperty("to")
    private String toKey;

    @JsonProperty("default")
    private String defaultValue;

    @JsonProperty("filter")
    private ResponseFilterDto filterDto;

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

    public ResponseFilterDto getFilterDto() {
        return filterDto;
    }

    public void setFilterDto(ResponseFilterDto filterDto) {
        this.filterDto = filterDto;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
