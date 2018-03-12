package edu.nc.travelplanner.model.jump;

import edu.nc.travelplanner.exception.CustomParseException;
import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.response.Response;

public interface Jump {
    Action getCurrentAction();
    Action getNextAction();
    boolean canJump(ActionArgs args, Response response);
}
