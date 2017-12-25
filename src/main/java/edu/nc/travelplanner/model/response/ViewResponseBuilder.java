package edu.nc.travelplanner.model.response;

import edu.nc.travelplanner.model.response.elements.*;

import java.util.Map;
import java.util.UUID;

public class ViewResponseBuilder {

    private ViewResponse viewResponse = new ViewResponse();

    public ViewResponseBuilder addTitleElement(String id, String data){
        viewResponse.addElement(new TitleViewElement(id, data));
        return this;
    }

    public ViewResponseBuilder addCheckboxes(Map<String, String> options){
        options.forEach((key, value) -> viewResponse.addElement(new CheckViewElement(key, value)));
        return this;
    }

    public ViewResponseBuilder addTextbox(String id, String data){
        viewResponse.addElement(new TextInputViewElement(id, data));
        return this;
    }

    public ViewResponseBuilder addDropdownList(String id, Map<String, String> options){
        viewResponse.addElement(new DropDownListViewElement(id, options));
        return this;
    }

    public ViewResponseBuilder addDatePicker(String id, String data){
        viewResponse.addElement(new DatePickerViewElement(id, data));
        return this;
    }

    public ViewResponse build(){
        return viewResponse;
    }

    private String generateId(){
        return UUID.randomUUID().toString();
    }
}
