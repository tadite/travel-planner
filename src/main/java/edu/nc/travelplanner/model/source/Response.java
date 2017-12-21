package edu.nc.travelplanner.model.source;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Response {
    String getRawData() throws JsonProcessingException;
}
