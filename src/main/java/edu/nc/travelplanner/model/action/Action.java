package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.response.Response;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public interface Action {
    String getName();
    ActionType getType();
    Response executeDecision(ActionArgs args);
    Response executePresentation(ActionArgs args);
    Object getResult(Map<String, String> decisionArgs);
}
