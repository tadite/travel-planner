package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.response.CheckboxListResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;

import java.util.HashMap;
import java.util.Map;

public class CheckboxListAction implements Action {

    private String name;
    private ActionType type = ActionType.CHECKBOX_LIST;
    private Map<String, String> optionsMap = new HashMap<>();

    public Map<String, String> getOptionsMap() {
        return optionsMap;
    }

    public void setOptionsMap(Map<String, String> optionsMap){
        this.optionsMap = optionsMap;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public Response executeDecision(ActionArgs args) {

        return new ViewResponseBuilder().addTitleElement(name + "_title" ,"decided").build();
    }

    @Override
    public Response executePresentation(ActionArgs args) {

        return new ViewResponseBuilder().addCheckboxes(optionsMap).build();
    }
}
