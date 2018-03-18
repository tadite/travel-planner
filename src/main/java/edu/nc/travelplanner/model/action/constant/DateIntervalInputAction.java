package edu.nc.travelplanner.model.action.constant;

import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DateIntervalInputAction implements Action {

    private String name;
    private String viewName;
    private String data;
    private ActionType type = ActionType.TEXT_INPUT;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getViewName() {
        return viewName;
    }

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public Response executeDecision(ActionArgs args, List<PickResult> pickResults) {
        return new EmptyResponse();
    }

    @Override
    public Response executePresentation(ActionArgs args, List<PickResult> pickResults) {
        return new ViewResponseBuilder().addTitleElement("question", viewName).addDatePicker(name, data).addDatePicker(name, data).build();
    }

    private String getEndDatePickerName() {
        return name + "-end-date-picker";
    }

    private String getStartDatePickerName() {
        return name + "-start-date-picker";
    }

    @Override
    public void getResult(Map<String, String> decisionArgs, List<PickResult> picks) {
        ViewResponseBuilder viewResponseBuilder = new ViewResponseBuilder();

        if (decisionArgs.containsValue(viewResponseBuilder.getDatePickerName(name, data, 1)) &&
                decisionArgs.containsValue(viewResponseBuilder.getDatePickerName(name, data, 2))) {
            String date1 = decisionArgs.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(viewResponseBuilder.getDatePickerName(name, data, 1)))
                    .findFirst().get().getKey();

            String date2 = decisionArgs.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(viewResponseBuilder.getDatePickerName(name, data, 2)))
                    .findFirst().get().getKey();

            picks.add(new PickResult(getName() + "1", date1));
            picks.add(new PickResult(getName() + "2", date2));
        }
    }
}
