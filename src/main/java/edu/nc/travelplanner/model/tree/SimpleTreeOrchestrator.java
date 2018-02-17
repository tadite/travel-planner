package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.factory.tree.ActionTreeFactory;
import edu.nc.travelplanner.model.factory.tree.ActionTreeParseException;
import edu.nc.travelplanner.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class SimpleTreeOrchestrator implements TreeOrchestrator {

    private ActionTree actionTree;

    @Value( "${travelplanner.maintree}" )
    private String treeName;

    public SimpleTreeOrchestrator(@Autowired ActionTreeFactory treeFactory){
        try {
            this.actionTree=treeFactory.createByName(treeName);
        } catch (ActionTreeParseException e) {
            e.printStackTrace();
        }
    }

    public Response executePresentation(ActionArgs args) {

        return actionTree.executePresentation(args);
    }

    public Response executeDecision(ActionArgs args) {
        return actionTree.executeDecision(args);
    }

    @Override
    public Response execute(ActionArgs args) {
        if (args.getActionState()== ActionState.DECISION)
            return executeDecision(args);
        else
            return executePresentation(args);
    }


}
