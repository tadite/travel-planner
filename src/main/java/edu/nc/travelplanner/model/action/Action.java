package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.response.Response;

public interface Action {
    String getName();
    ActionType getType();
    Response executeDecision(ActionArgs args);
    Response executePresentation(ActionArgs args);
}
