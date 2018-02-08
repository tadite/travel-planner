package edu.nc.travelplanner.model.source.parametermapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PickResultParameterMapper implements ParameterMapper {

    @JsonProperty("from")
    private String fromKey;

    @JsonProperty("to")
    private String toKey;

    public PickResultParameterMapper() {
    }

    public PickResultParameterMapper(String fromKey, String toKey) {
        this.fromKey = fromKey;
        this.toKey = toKey;
    }

    @Override
    public String map(String key) {
        return key.equals(fromKey)?toKey:null;
    }
}
