package edu.nc.travelplanner.model.response;

import edu.nc.travelplanner.model.response.elements.CheckboxViewElement;
import edu.nc.travelplanner.model.response.elements.TitleViewElement;
import edu.nc.travelplanner.model.response.elements.ViewElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ViewResponseBuilder {

    private ViewResponse viewResponse = new ViewResponse();

    public ViewResponseBuilder addTitleElement(String id, String data){
        viewResponse.addElement(new TitleViewElement(id, data));
        return this;
    }

    public ViewResponseBuilder addCheckboxes(Map<String, String> options){
        options.forEach((key, value) -> viewResponse.addElement(new CheckboxViewElement(key, value)));
        return this;
    }

    public ViewResponse build(){
        return viewResponse;
    }

    private String generateId(){
        return UUID.randomUUID().toString();
    }
}
