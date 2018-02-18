package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.factory.tree.ActionTreeFactory;
import edu.nc.travelplanner.model.factory.tree.ActionTreeParseException;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.TravelResultResponse;
import edu.nc.travelplanner.model.resultsMapper.ResultsMapperReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class SimpleTreeOrchestrator implements TreeOrchestrator {

    private ActionTree actionTree;

    @Value( "${travelplanner.maintree}" )
    private String treeName;

    ResultsMapperReader resultsMapperReader;

    private TravelAfterPickTreeDto travelAfterPickTreeDto;

    public SimpleTreeOrchestrator(@Autowired ActionTreeFactory treeFactory, @Autowired ResultsMapperReader resultsMapperReader){
        try {
            this.resultsMapperReader=resultsMapperReader;
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
        if (travelAfterPickTreeDto!=null)
            return new TravelResultResponse(travelAfterPickTreeDto);
        else if (actionTree.isEnded())
        {
            try {
                travelAfterPickTreeDto = resultsMapperReader.read(treeName).map(actionTree.getPickResults());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new TravelResultResponse(travelAfterPickTreeDto);
        }

        if (args.getActionState()== ActionState.DECISION)
            return executeDecision(args);
        else
            return executePresentation(args);
    }


}
