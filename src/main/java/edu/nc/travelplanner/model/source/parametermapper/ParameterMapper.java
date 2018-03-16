package edu.nc.travelplanner.model.source.parametermapper;

public interface ParameterMapper {
    String map(String key);
    String filterValue(String value);
    String getToKey();
    String getFromKey();
    String getDefaultValue();
}
