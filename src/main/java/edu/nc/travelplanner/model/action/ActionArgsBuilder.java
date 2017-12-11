package edu.nc.travelplanner.model.action;

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

    public ActionArgs build() {
        return args;
    }
}
