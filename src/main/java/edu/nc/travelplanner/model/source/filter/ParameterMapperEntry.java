package edu.nc.travelplanner.model.source.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ParameterMapperEntry {
    private String path;
    private String name;
    @JsonIgnore
    private String[] pathArray;

    public ParameterMapperEntry(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPathArray() {
        return pathArray;
    }

    public void setPathArray(String[] pathArray) {
        this.pathArray = pathArray;
    }
}
