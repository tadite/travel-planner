package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.config.AuthenticationFacade;
import edu.nc.travelplanner.dao.ClientDao;
import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.exception.CustomParseException;
import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.factory.tree.ActionTreeFactory;
import edu.nc.travelplanner.model.factory.tree.ActionTreeParseException;
import edu.nc.travelplanner.model.response.*;
import edu.nc.travelplanner.model.resultsMapper.ResultsMapperReader;
import edu.nc.travelplanner.service.travel.TravelSaveService;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class SimpleTreeOrchestrator implements TreeOrchestrator {

    @Autowired
    private ApplicationContext context;

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    ClientDao clientDao;

    @Autowired
    TravelSaveService travelSaveService;

    private ActionTree actionTree;

    @Value( "${travelplanner.maintree}" )
    private String treeName;

    @Value("${travelplanner.requestAttemptsCount}")
    private String requestAttemtsCount;

    @Value("${travelplanner.requestWaitAfterFailSecs}")
    private String requestWaitAfterFailSecs;

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
                this.actionTree.setRequestAttemptParams(Integer.valueOf(requestAttemtsCount), Integer.valueOf(requestWaitAfterFailSecs));
            } catch (ActionTreeParseException e) {
                e.printStackTrace();
            }
        }
        return actionTree;
    }

    public Response executePresentation(ActionArgs args) {

        return getActionTree().executePresentation(args);
    }

    public Response executeDecision(ActionArgs args) throws CustomParseException {
        return getActionTree().executeDecision(args);
    }

    @Override
    public Response execute(ActionArgs args) throws CustomParseException {
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

    @Override
    public Response rollback() {
        this.actionTree.rollback();
        return new EmptyResponse();
    }

    @Override
    public void reset() {
        this.travelAfterPickTreeDto=null;
        this.actionTree=null;
    }

    private void saveTravelToDb(TravelAfterPickTreeDto travelAfterPickTreeDto) {
        travelAfterPickTreeDto.setClientId(clientDao.getClientByLogin(authenticationFacade.getAuthentication().getName()).getClientId());

        travelSaveService.saveTravelAfterPick(travelAfterPickTreeDto);
    }


}
