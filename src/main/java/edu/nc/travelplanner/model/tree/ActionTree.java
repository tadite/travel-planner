package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.response.Response;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ActionTree {
    String getName();
    void addAllJumps(Collection<Jump> jumps);
    void addJump(Jump jump);
    Response executePresentation(ActionArgs args);
    Response executeDecision(ActionArgs args);
    List<PickResult> getPickResults();
    Boolean isEnded();
}
