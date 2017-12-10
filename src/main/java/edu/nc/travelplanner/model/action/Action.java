package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.source.Response;

import java.util.List;

public interface Action {
    String getName();
    ActionType getType();
    List<Jump> getJumps();
    Response executeDecision(ActionArgs args);
    Response executePresentation(ActionArgs args);
}
