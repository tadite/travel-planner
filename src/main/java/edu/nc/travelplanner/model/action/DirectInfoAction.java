package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.source.Response;
import edu.nc.travelplanner.model.source.TextResponse;

import java.util.Collections;
import java.util.List;

public class DirectInfoAction implements Action {

    private String name;
    private List<Jump> jumps;
    private String data;

    public DirectInfoAction(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ActionType getType() {
        return ActionType.DIRECT_INFO;
    }

    @Override
    public List<Jump> getJumps() {
        return Collections.unmodifiableList(jumps);
    }

    @Override
    public Response executeDecision(ActionArgs args) {
        return null;
    }

    @Override
    public Response executePresentation() {
        return new TextResponse(data);
    }
}
