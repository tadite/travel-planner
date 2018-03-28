package edu.nc.travelplanner.model.source.parametermapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;

import java.io.IOException;
import java.util.Map;

public class PickResultParameterMapper implements ParameterMapper {

    @JsonProperty("from")
    private String fromKey;

    @JsonProperty("to")
    private String toKey;

    @JsonProperty("default")
    private String defaultValue;

    private ResponseFilter filter;

    public PickResultParameterMapper() {
    }

    public PickResultParameterMapper(String fromKey, String toKey) {
        this.fromKey = fromKey;
        this.toKey = toKey;
    }

    public PickResultParameterMapper(String fromKey, String toKey, ResponseFilter filter) {
        this.fromKey = fromKey;
        this.toKey = toKey;
        this.filter=filter;
    }

    public PickResultParameterMapper(String fromKey, String toKey, ResponseFilter filter, String defaultValue) {
        this.fromKey = fromKey;
        this.toKey = toKey;
        this.filter=filter;
        this.defaultValue=defaultValue;
    }

    @Override
    public String map(String key) {
        return key.equals(fromKey) ? toKey : null;
    }

    @Override
    public String filterValue(String value, Map<String, String> params) throws IOException {
        if (filter!=null && value!=null)
            return filter.filter(value, params);
        return value;
    }

    @Override
    public String getToKey() {
        return toKey;
    }

    @Override
    public String getFromKey() {
        return fromKey;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }
}
