package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.source.Response;

public interface Jump {
    Action getCurrent();
    Action getNext();
    boolean canJump(ActionArgs args, Response response);
}
