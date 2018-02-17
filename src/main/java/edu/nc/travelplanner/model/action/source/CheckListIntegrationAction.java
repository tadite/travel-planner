package edu.nc.travelplanner.model.action.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.IntegrationAction;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;
import edu.nc.travelplanner.model.source.dataproducer.DataProducer;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CheckListIntegrationAction implements IntegrationAction {

    private String name;
    private String viewName;
    private ActionType type = ActionType.CHECKLIST_INTEGRATION;
    private Map<String, String> optionsMap = new HashMap<>();
    private DataProducer dataProducer;

    ObjectMapper objectMapper = new ObjectMapper();

    public CheckListIntegrationAction() {
    }

    public CheckListIntegrationAction(String name, String viewName, DataProducer dataProducer) {
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
    public Response executePresentation(ActionArgs args, List<PickResult> pickResults) {

        try {
            Response response = dataProducer.send(pickResults);
            saveOptionsMap(response);
            return new ViewResponseBuilder().addTitleElement("question", viewName).addCheckboxes(optionsMap).build();
        } catch (DataProducerParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //TODO: new id generation getResults
    private void saveOptionsMap(Response response) throws IOException {
        optionsMap = objectMapper.readValue(response.getRawData(), Map.class);

    }

    @Override
    public Object getResult(Map<String, String> decisionArgs) {
        Set<Map.Entry<String,String>> results = new HashSet<>(decisionArgs.entrySet().stream()
                .filter((entry) -> optionsMap.containsKey(entry.getKey())).collect(Collectors.toSet()));

        if (!results.isEmpty()){
            Set<String> pickedValues = new HashSet<>();
            results.iterator().forEachRemaining(entry -> pickedValues.add(entry.getKey()));
            return pickedValues;
        }
        return null;
    }

    public DataProducer getDataProducer() {
        return dataProducer;
    }
}
