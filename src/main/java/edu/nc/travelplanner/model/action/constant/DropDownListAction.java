package edu.nc.travelplanner.model.action.constant;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DropDownListAction implements Action {

    private String name;
    private ActionType type = ActionType.CHECKLIST;
    private Map<String, String> optionsMap = new HashMap<>();

    public DropDownListAction() {
    }

    public DropDownListAction(String name, Map<String, String> optionsMap) {
        this.name = name;
        this.optionsMap=optionsMap;
    }

    @Override
    public String getName() {
        return name;
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
        return new ViewResponseBuilder().addDropdownList(getDropdownElementName(), optionsMap).build();
    }

    @Override
    public String getResult(Map<String, String> decisionArgs) {
        Optional<Map.Entry<String, String>> first = decisionArgs.entrySet().stream()
                .filter((entry) -> optionsMap.containsKey(entry.getKey())).findFirst();
        if (first.isPresent())
            return first.get().getKey();
        return null;
    }

    private String getDropdownElementName(){
        return name+"-dropdown-list";
    }
}
