package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
        return new ViewResponseBuilder().addDatePicker(getStartDatePickerName(), data).addDatePicker(getEndDatePickerName(), data).build();
    }

    private String getEndDatePickerName() {
        return name+"-end-date-picker";
    }

    private String getStartDatePickerName() {
        return name+"-start-date-picker";
    }

    @Override
    public DateInterval getResult(Map<String, String> decisionArgs) throws ParseException {
        if (decisionArgs.containsKey(getEndDatePickerName()) && decisionArgs.containsKey(getStartDatePickerName()))
        {
            String string = "January 2, 2010";
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            return new DateInterval(format.parse(decisionArgs.get(getStartDatePickerName())),
                    format.parse(decisionArgs.get(getEndDatePickerName())));
        }
        return null;
    }
}
