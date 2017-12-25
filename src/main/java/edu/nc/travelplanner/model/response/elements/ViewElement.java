package edu.nc.travelplanner.model.response.elements;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ViewElement {
    ViewElementType getType();
    String getId();
    Object getData() throws JsonProcessingException;
}
