package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.source.Response;
import edu.nc.travelplanner.model.source.TextResponse;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class InfoAction implements Action {

    private String name;
    private List<Jump> jumps = new LinkedList<>();
    private String data;

    public InfoAction(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public void addJump(Jump jump){
        this.jumps.add(jump);
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

        return new TextResponse("decided");
    }

    //TODO: in presentation same names as in decision
    @Override
    public Response executePresentation(ActionArgs args) {

        return new TextResponse(data);
    }
}
