package edu.nc.travelplanner.model.response.elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class DropDownListViewElement implements ViewElement {

    private String id;
    private Map<String, String> optionsMap = new HashMap<>();

    public DropDownListViewElement(String id, Map<String, String> optionsMap) {
        this.id = id;
        this.optionsMap = optionsMap;
    }

    @Override
    public ViewElementType getType() {
        return ViewElementType.DROPDOWN_TEXT_LIST;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Object getData() throws JsonProcessingException {
        return optionsMap;
    }
}
