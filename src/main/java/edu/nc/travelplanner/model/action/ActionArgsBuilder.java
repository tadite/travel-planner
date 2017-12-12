package edu.nc.travelplanner.model.action;

import java.util.Map;

public class ActionArgsBuilder {

    private ActionArgs args = new ActionArgs();

    public ActionArgsBuilder setState(ActionState state){
        args.setActionState(state);
        return this;
    }

    public ActionArgsBuilder addArg(String key, String value){
        args.addArg(key,value);
        return this;
    }


    public ActionArgsBuilder addAllArgs(Map<String, String> argsMap){
        args.addAllArg(argsMap);
        return this;
    }

    public ActionArgs build() {
        return args;
    }
}
