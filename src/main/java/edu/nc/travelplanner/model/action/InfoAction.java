package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.model.source.Response;
import edu.nc.travelplanner.model.source.TextResponse;

public class InfoAction implements Action {

    private String name;
    private String data;
    private ActionType type = ActionType.INFO;

    public InfoAction() {
    }

    public InfoAction(String name, String data) {
        this.name = name;
        this.data = data;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public Response executeDecision(ActionArgs args) {

        return new TextResponse("decided");
    }

    @Override
    public Response executePresentation(ActionArgs args) {

        return new TextResponse(data);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
