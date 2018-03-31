package edu.nc.travelplanner.model.tree;

import edu.nc.travelplanner.config.AuthenticationFacade;
import edu.nc.travelplanner.dto.afterPickTree.TravelDto;
import edu.nc.travelplanner.exception.CustomParseException;
import edu.nc.travelplanner.model.action.*;
import edu.nc.travelplanner.model.factory.tree.ActionTreeFactory;
import edu.nc.travelplanner.model.factory.tree.ActionTreeParseException;
import edu.nc.travelplanner.model.response.*;
import edu.nc.travelplanner.model.resultsMapper.ResultsMapper;
import edu.nc.travelplanner.service.travel.TravelSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.NotSupportedException;
import java.text.ParseException;

@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class SimpleTreeOrchestrator implements TreeOrchestrator {

    @Autowired
    TravelSaveService travelSaveService;

    @Autowired
    AuthenticationFacade authenticationFacade;

    private ActionTree actionTree;

    private String treeUsername;

    @Value("${travelplanner.maintree}")
    private String treeName;

    @Value("${travelplanner.requestAttemptsCount}")
    private String requestAttemtsCount;

    @Value("${travelplanner.requestWaitAfterFailSecs}")
    private String requestWaitAfterFailSecs;

    @Autowired
    ResultsMapper resultsMapper;

    private TravelDto travelDto;
    private ActionTreeFactory treeFactory;

    public SimpleTreeOrchestrator(@Autowired ActionTreeFactory treeFactory) {
        this.treeFactory = treeFactory;
    }

    private ActionTree getActionTree() {

        if (actionTree == null) {
            try {
                this.actionTree = treeFactory.createByName(treeName);
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

    private void checkUser(){
        String currentUsername = authenticationFacade.getAuthentication().getName();
        if (treeUsername == null)
            treeUsername = currentUsername;
        else if (!treeUsername.equals(currentUsername)) {
            treeUsername = currentUsername;
            this.actionTree = null;
        }
    }

    @Override
    public Response execute(ActionArgs args) throws CustomParseException {
        checkUser();

        if (travelDto != null)
            return new TravelResultResponse(travelDto);
        else if (getActionTree().isEnded()) {
            travelDto = resultsMapper.map(getActionTree().getPickResults());

            return new TravelResultResponse(travelDto);
        }

        if (args.getActionState() == ActionState.DECISION)
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
        this.travelDto = null;
        this.actionTree = null;
    }

    @Override
    public TravelDto save() throws ParseException, NotSupportedException {
        if (travelDto == null)
            throw new NotSupportedException();

        return saveTravelToDb(travelDto);
    }

    private TravelDto saveTravelToDb(TravelDto travelDto) throws ParseException, NotSupportedException {
        travelDto = travelSaveService.save(travelDto);
        return travelDto;
    }

}
