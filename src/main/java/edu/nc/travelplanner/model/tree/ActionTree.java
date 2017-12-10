package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.source.Response;

public interface ActionTree {
    String getName();
    Response executePresentation(ActionArgs args);
    Response executeDecision(ActionArgs args);
}
