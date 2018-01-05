package edu.nc.travelplanner.model.source;

public interface Source {
    String getUrl();
    String getName();
    SourceType getType();
}
