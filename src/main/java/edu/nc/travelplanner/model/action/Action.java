package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.response.Response;

import java.util.List;
import java.util.Map;

public interface Action {
    String getName();
    ActionType getType();
    Response executeDecision(ActionArgs args, List<PickResult> pickResult);
    Response executePresentation(ActionArgs args, List<PickResult> pickResult);
    Object getResult(Map<String, String> decisionArgs);
}
