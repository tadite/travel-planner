package edu.nc.travelplanner.model.action;

import java.util.HashMap;
import java.util.Map;

public class ActionArgs {
    private HashMap<String, String> args = new HashMap<>();

    public void addArg(String key, String value){
        args.put(key, value);
    }

    public String getValue(String key){
        return args.get(key);
    }

    public Map<String, String> getArgs(){
        return new HashMap<>(args);
    }
}
