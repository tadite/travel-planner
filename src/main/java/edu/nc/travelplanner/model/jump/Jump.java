package edu.nc.travelplanner.model.jump;

import edu.nc.travelplanner.exception.CustomParseException;
import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.Response;

import java.util.List;

public interface Jump {
    Action getCurrentAction();
    Action getNextAction();
    boolean canJump(ActionArgs args, List<PickResult> pickResults, Response response);
}
