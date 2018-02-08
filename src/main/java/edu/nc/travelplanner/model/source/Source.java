package edu.nc.travelplanner.model.source;

public interface Source {
    String getUrl();
    String getUrlWithParameterValues();
    String getName();
    SourceType getType();
    void addParameterValue(String name, String value);
}
