package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.source.Response;

public class SimpleTreeOrchestrator implements TreeOrchestrator {

    ActionTree actionTree;

    public SimpleTreeOrchestrator(ActionTree actionTree){
        this.actionTree = actionTree;
    }

    @Override
    public Response executePresentation() {
        return actionTree.executePresentation();
    }

    @Override
    public Response executeDecision(ActionArgs args) {
        return actionTree.executeDecision(args);
    }
}
