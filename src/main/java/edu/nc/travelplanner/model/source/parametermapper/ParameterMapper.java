package edu.nc.travelplanner.model.source.parametermapper;

import java.io.IOException;
import java.util.Map;

public interface ParameterMapper {
    String map(String key);
    String filterValue(String value, Map<String, String> tempParameterValues) throws IOException;
    String getToKey();
    String getFromKey();
    String getDefaultValue();
}
