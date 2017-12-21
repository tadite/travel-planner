package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.source.CheckboxListResponse;
import edu.nc.travelplanner.model.source.Response;
import edu.nc.travelplanner.model.source.TextResponse;

import java.util.HashMap;
import java.util.Map;

public class CheckboxListAction implements Action {

    private String name;
    private ActionType type = ActionType.CHECKBOX_LIST;
    private Map<String, String> optionsMap = new HashMap<>();

    public Map<String, String> getOptionsMap() {
        return optionsMap;
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
        return new TextResponse("decided");
    }

    @Override
    public Response executePresentation(ActionArgs args) {
        return new CheckboxListResponse(optionsMap);
    }
}
