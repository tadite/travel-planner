package edu.nc.travelplanner.model.action;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class ActionArgs {
    private Map<String, String> args = new HashMap<>();

    @JsonProperty("state")
    private ActionState actionState;

    public ActionArgs(ActionState actionState){
        this.actionState=actionState;
    }

    public void addArg(String key, String value){
        args.put(key, value);
    }

    public String getValue(String key){
        return args.get(key);
    }

    public Map<String, String> getArgs(){
        return new HashMap<>(args);
    }

    public ActionState getActionState() {
        return actionState;
    }

    public void setActionState(ActionState actionState) {
        this.actionState = actionState;
    }
}
