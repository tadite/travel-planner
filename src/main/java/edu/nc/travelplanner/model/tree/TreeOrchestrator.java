package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.source.Response;

public interface TreeOrchestrator {
    Response execute(ActionArgs args);
}
