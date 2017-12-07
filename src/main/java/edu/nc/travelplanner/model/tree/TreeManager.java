package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.source.Response;

public interface TreeManager {
    Response executePresentation();
    Response executeDecision(ActionArgs args);
}
