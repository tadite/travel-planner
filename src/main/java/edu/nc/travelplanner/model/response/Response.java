package edu.nc.travelplanner.model.response;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Response {
    String getRawData();
    void setRawData(String rawData);
}
