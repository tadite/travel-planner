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

public class CheckListAction implements Action {

    private String name;
    private String viewName;
    private ActionType type = ActionType.CHECKLIST;
    private Map<String, String> optionsMap = new HashMap<>();


    public CheckListAction() {
    }

    public CheckListAction(String name) {
        this.name = name;
    }

    public CheckListAction(String name, String viewName, Map<String, String> optionsMap) {
        this.name = name;
        this.viewName=viewName;
        this.optionsMap=optionsMap;
    }

    public void setOptionsMap(Map<String, String> optionsMap){
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

        return new ViewResponseBuilder().addTitleElement("question", viewName).addCheckboxes(optionsMap).build();
    }

    @Override
    public List<String> getResult(Map<String, String> decisionArgs) {
        Set<Map.Entry<String,String>> results = new HashSet<> (decisionArgs.entrySet().stream()
                .filter((entry) -> optionsMap.containsKey(entry.getKey().substring(entry.getKey().lastIndexOf('.')+1))).collect(Collectors.toSet()));

        if (!results.isEmpty()){
            List<String> pickedValues = new LinkedList<>();
            results.iterator().forEachRemaining(entry -> pickedValues.add(entry.getKey().substring(entry.getKey().lastIndexOf('.')+1)));
            return pickedValues;
        }

        return null;
    }
}
