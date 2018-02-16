package edu.nc.travelplanner.model.action.constant;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;

import java.util.List;
import java.util.Map;

public class InfoAction implements Action {

    private String name;
    private String viewName;
    private String data;
    private ActionType type = ActionType.INFO;

    public InfoAction() {
    }

    public InfoAction(String name, String viewName, String data) {
        this.name = name;
        this.viewName = viewName;
        this.data = data;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getViewName() {
        return viewName;
    }
    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public Response executeDecision(ActionArgs args, List<PickResult> pickResults) {
        return new EmptyResponse();
    }

    @Override
    public Response executePresentation(ActionArgs args, List<PickResult> pickResults) {
        return new ViewResponseBuilder().addTitleElement("question", viewName).addTitleElement(name+"-title", data).build();
    }

    @Override
    public String getResult(Map<String, String> decisionArgs) {
        return null;
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
