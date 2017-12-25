package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;

import java.util.Map;

public class DateIntervalInputAction implements Action {

    private String name;
    private String data;
    private ActionType type = ActionType.TEXT_INPUT;

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
        return new EmptyResponse();
    }

    @Override
    public Response executePresentation(ActionArgs args) {
        return new ViewResponseBuilder().addDatePicker(name+"-start-date-picker", data).addDatePicker(name+"-end-date-picker", data).build();
    }

    @Override
    public String getResult(Map<String, String> decisionArgs) {
        return null;
    }
}
