package edu.nc.travelplanner.model.action.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.exception.CustomParseException;
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

import java.io.IOException;
import java.util.*;

public class RadioListIntegrationAction implements IntegrationAction {

    private String name;
    private String viewName;
    private ActionType type = ActionType.RADIOLIST_INTEGRATION;
    private Map<String, String> optionsMap = new HashMap<>();
    private DataProducer dataProducer;

    ObjectMapper objectMapper = new ObjectMapper();

    public RadioListIntegrationAction() {
    }

    public RadioListIntegrationAction(String name, String viewName, DataProducer dataProducer) {
        this.name=name;
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
    public Response executePresentation(ActionArgs args, List<PickResult> pickResults) throws DataProducerSendException, CustomParseException, NotEnoughParamsException {

        try {
            Response response = dataProducer.send(pickResults);
            saveOptionsMap(response);
            return new ViewResponseBuilder().addTitleElement("question", viewName).addRadioboxes(optionsMap).build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomParseException();
        }
    }
    //TODO: new id generation getResults
    private void saveOptionsMap(Response response) throws IOException {
        optionsMap = objectMapper.readValue(response.getRawData(), Map.class);

    }

    @Override
    public void getResult(Map<String, String> decisionArgs, List<PickResult> picks) {

        String key = decisionArgs.entrySet().stream()
                .filter((entry) ->
                        optionsMap.containsKey(entry.getKey()
                                .substring(entry.getKey()
                                        .lastIndexOf('.') + 1)))
                .findFirst()
                .map(stringStringEntry -> stringStringEntry.getKey()
                        .substring(stringStringEntry.getKey().lastIndexOf('.') + 1))
                .orElse(null);

        picks.add(new PickResult(getName(), key));
        picks.add(new PickResult(getName() + ".Value", optionsMap.get(key)));
    }

    public DataProducer getDataProducer() {
        return dataProducer;
    }

    public Map<String, String> getOptionsMap() {
        return optionsMap;
    }
}
