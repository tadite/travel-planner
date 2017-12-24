package edu.nc.travelplanner.model.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class CheckboxListResponse implements Response {

    @Autowired
    ObjectMapper objectMapper;

    private Map<String, String> optionsMap;

    public CheckboxListResponse(Map<String, String> optionsMap) {
        this.optionsMap = optionsMap;
    }

    @Override
    public String getRawData() throws JsonProcessingException {
        return objectMapper.writeValueAsString(optionsMap);
    }
}
