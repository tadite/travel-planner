package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.response.Response;

public interface TreeOrchestrator {
    Response execute(ActionArgs args);
}
