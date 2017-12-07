package edu.nc.travelplanner.model.source;

public class EmptyResponse implements Response {
    @Override
    public String getRawData() {
        return "";
    }
}
