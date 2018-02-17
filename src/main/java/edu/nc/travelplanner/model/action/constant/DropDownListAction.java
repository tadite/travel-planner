package edu.nc.travelplanner.model.action.constant;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class DropDownListAction implements Action {

    private String name;
    private String viewName;
    private ActionType type = ActionType.CHECKLIST;
    private Map<String, String> optionsMap = new HashMap<>();

    public DropDownListAction() {
    }

    public DropDownListAction(String name, String viewName, Map<String, String> optionsMap) {
        this.name = name;
        this.viewName = viewName;
        this.optionsMap=optionsMap;
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
        return new ViewResponseBuilder().addTitleElement("question", viewName).addDropdownList(name, optionsMap).build();
    }

    @Override
    public String getResult(Map<String, String> decisionArgs) {
        Optional<Map.Entry<String, String>> first = decisionArgs.entrySet().stream()
                .filter((entry) -> optionsMap.containsKey(entry.getKey().substring(entry.getKey().lastIndexOf('.') + 1))).findFirst();

        if (first.isPresent())
            return first.get().getKey().substring(first.get().getKey().lastIndexOf('.') + 1);
        return null;
    }

    private String getDropdownElementName(){
        return name+"-dropdown-list";
    }
}
