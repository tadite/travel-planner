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
import java.util.Optional;

public class TextInputAction implements Action {

    private String name;
    private String viewName;
    private String data;
    private ActionType type = ActionType.TEXT_INPUT;

    public TextInputAction() {
    }

    public TextInputAction(String name, String viewName, String data) {
        this.name = name;
        this.viewName = viewName;
        this.data=data;
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
        return new ViewResponseBuilder().addTitleElement("question", viewName).addTextbox(name, data).build();
    }

    @Override
    public void getResult(Map<String, String> decisionArgs, List<PickResult> picks) {
        Optional<Map.Entry<String, String>> first = decisionArgs.entrySet().stream()
                .filter((entry) -> entry.getValue().endsWith(name)).findFirst();
        if (first.isPresent()) {
            String key = first.get().getKey();
            picks.add(new PickResult(getName(), key));
        }
    }

    private String getTextboxElementName(){
        return name+"-textbox";
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
