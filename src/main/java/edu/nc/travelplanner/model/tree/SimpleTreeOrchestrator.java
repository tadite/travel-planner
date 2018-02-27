package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.config.AuthenticationFacade;
import edu.nc.travelplanner.dao.ClientDao;
import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.factory.tree.ActionTreeFactory;
import edu.nc.travelplanner.model.factory.tree.ActionTreeParseException;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.TravelResultResponse;
import edu.nc.travelplanner.model.resultsMapper.ResultsMapperReader;
import edu.nc.travelplanner.service.travel.TravelService;
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

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    ClientDao clientDao;

    @Autowired
    TravelService travelService;

    private ActionTree actionTree;

    @Value( "${travelplanner.maintree}" )
    private String treeName;

    ResultsMapperReader resultsMapperReader;

    private TravelAfterPickTreeDto travelAfterPickTreeDto;
    private ActionTreeFactory treeFactory;

    public SimpleTreeOrchestrator(@Autowired ActionTreeFactory treeFactory, @Autowired ResultsMapperReader resultsMapperReader){
        this.treeFactory = treeFactory;
        this.resultsMapperReader=resultsMapperReader;
    }

    private ActionTree getActionTree(){
        if (actionTree==null) {
            try {
                this.actionTree=treeFactory.createByName(treeName);
            } catch (ActionTreeParseException e) {
                e.printStackTrace();
            }
        }
        return actionTree;
    }

    public Response executePresentation(ActionArgs args) {

        return getActionTree().executePresentation(args);
    }

    public Response executeDecision(ActionArgs args) {
        return getActionTree().executeDecision(args);
    }

    @Override
    public Response execute(ActionArgs args) {
        if (travelAfterPickTreeDto!=null)
            return new TravelResultResponse(travelAfterPickTreeDto);
        else if (getActionTree().isEnded())
        {
            try {
                travelAfterPickTreeDto = resultsMapperReader.read(treeName).map(getActionTree().getPickResults());
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveTravelToDb(travelAfterPickTreeDto);
            return new TravelResultResponse(travelAfterPickTreeDto);
        }

        if (args.getActionState()== ActionState.DECISION)
            return executeDecision(args);
        else
            return executePresentation(args);
    }

    private void saveTravelToDb(TravelAfterPickTreeDto travelAfterPickTreeDto) {
        travelAfterPickTreeDto.setClientId(clientDao.getClientByLogin(authenticationFacade.getAuthentication().getName()).getClientId());

        travelService.saveTravelAfterPick(travelAfterPickTreeDto);
    }


}
