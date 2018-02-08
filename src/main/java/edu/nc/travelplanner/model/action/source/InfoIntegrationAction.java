package edu.nc.travelplanner.model.action.source;

import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.IntegrationAction;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;
import edu.nc.travelplanner.model.source.dataproducer.DataProducer;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;

import java.util.List;
import java.util.Map;

public class InfoIntegrationAction implements IntegrationAction {

    private String name;
    private ActionType type = ActionType.INFO_INTEGRATION;
    private String data;

    private DataProducer dataProducer;

    public InfoIntegrationAction() {
    }

    public InfoIntegrationAction(String name, DataProducer dataProducer) {
        this.name = name;
        this.dataProducer = dataProducer;
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
    public Response executePresentation(ActionArgs args, List<PickResult> pickResults) {

        try {
            Response response = dataProducer.send(pickResults);
            return new ViewResponseBuilder().addTitleElement(getTitleId(), response.getRawData()).build();
        } catch (DataProducerParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getResult(Map<String, String> decisionArgs) {
        return null;
    }

    private String getTitleId(){
        return name+"-title";
    }
}
