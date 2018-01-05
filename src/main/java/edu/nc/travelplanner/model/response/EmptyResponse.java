package edu.nc.travelplanner.model.response;

public class EmptyResponse implements Response {
    @Override
    public String getRawData() {
        return "";
    }

    @Override
    public void setRawData(String rawData) {

    }
}
