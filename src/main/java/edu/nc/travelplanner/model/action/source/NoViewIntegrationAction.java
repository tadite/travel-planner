package edu.nc.travelplanner.model.action.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.exception.DataProducerSendException;
import edu.nc.travelplanner.exception.NotEnoughParamsException;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.IntegrationAction;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;
import edu.nc.travelplanner.model.source.dataproducer.DataProducer;

import java.util.List;
import java.util.Map;

public class NoViewIntegrationAction implements IntegrationAction {

    private String name;
    private String viewName;
    private ActionType type = ActionType.NO_VIEW_TEXT_INTEGRAION;
    private DataProducer dataProducer;

    ObjectMapper objectMapper = new ObjectMapper();

    private String result;

    public NoViewIntegrationAction() {
    }

    public NoViewIntegrationAction(String name, String viewName, DataProducer dataProducer) {
        this.name = name;
        this.viewName = viewName;
        this.dataProducer = dataProducer;
    }

    @Override
    public String getViewName() {
        return viewName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public Response executeDecision(ActionArgs args, List<PickResult> pickResults) {

        return new EmptyResponse();
    }

    @Override
    public Response executePresentation(ActionArgs args, List<PickResult> pickResults) throws DataProducerSendException, NotEnoughParamsException {

        Response response = dataProducer.send(pickResults);
        saveResult(response);
        return new ViewResponseBuilder().build();

    }

    private void saveResult(Response response) {
        this.result = response.getRawData();
    }

    @Override
    public void getResult(Map<String, String> decisionArgs, List<PickResult> picks) {

        picks.add(new PickResult(this.getName(), result));
    }

    public DataProducer getDataProducer() {
        return dataProducer;
    }
}
