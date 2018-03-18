package edu.nc.travelplanner.model.action.constant;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;

import java.util.*;

public class RadioListAction implements Action {

    private String name;
    private String viewName;
    private ActionType type = ActionType.RADIOLIST;
    private Map<String, String> optionsMap = new HashMap<>();


    public RadioListAction() {
    }

    public RadioListAction(String name) {
        this.name = name;
    }

    public RadioListAction(String name, String viewName, Map<String, String> optionsMap) {
        this.name = name;
        this.viewName = viewName;
        this.optionsMap = optionsMap;
    }

    public void setOptionsMap(Map<String, String> optionsMap) {
        this.optionsMap = optionsMap;
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

    public Map<String, String> getOptionsMap() {
        return optionsMap;
    }

    @Override
    public Response executeDecision(ActionArgs args, List<PickResult> pickResults) {

        return new EmptyResponse();
    }

    @Override
    public Response executePresentation(ActionArgs args, List<PickResult> pickResults) {

        return new ViewResponseBuilder().addTitleElement("question", viewName).addRadioboxes(optionsMap).build();
    }

    @Override
    public void getResult(Map<String, String> decisionArgs, List<PickResult> picks) {

        String key = decisionArgs.entrySet().stream()
                .filter((entry) ->
                        optionsMap.containsKey(entry.getKey()
                                .substring(entry.getKey()
                                        .lastIndexOf('.') + 1)))
                .findFirst()
                .map(stringStringEntry -> stringStringEntry.getKey()
                        .substring(stringStringEntry.getKey().lastIndexOf('.') + 1))
                .orElse(null);

        picks.add(new PickResult(getName(), key));
        picks.add(new PickResult(getName() + ".Value", optionsMap.get(key)));

    }
}
