package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.response.Response;

import java.io.IOException;

public interface TreeOrchestrator {
    Response execute(ActionArgs args);
}
