package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.source.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)

@Scope(value="session", proxyMode =ScopedProxyMode.TARGET_CLASS)
@Component
public class SimpleTreeOrchestrator implements TreeOrchestrator {

    @Autowired
    private ActionTreeFactory treeFactory;

    private ActionTree actionTree;

    public SimpleTreeOrchestrator(){
        this.actionTree=treeFactory.createByName("test-tree");
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
            return executeDecision(args);
    }
}
