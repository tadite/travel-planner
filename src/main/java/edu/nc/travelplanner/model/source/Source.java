package edu.nc.travelplanner.model.source;

import edu.nc.travelplanner.exception.NotEnoughParamsException;

public interface Source {
    String getUrl();
    String getUrlWithParameterValues() throws NotEnoughParamsException;
    String getName();
    SourceType getType();
    void addParameterValue(String name, String value);
    Boolean isAllParamsHaveValue();
}
