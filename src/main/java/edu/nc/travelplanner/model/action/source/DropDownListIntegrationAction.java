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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DropDownListIntegrationAction implements IntegrationAction {

    private String name;
    private String viewName;
    private ActionType type = ActionType.DROPDOWN_INTEGRATION;
    private Map<String, String> optionsMap = new HashMap<>();

    private DataProducer dataProducer;

    ObjectMapper objectMapper = new ObjectMapper();

    public DropDownListIntegrationAction() {
    }

    public DropDownListIntegrationAction(String name, String viewName, DataProducer dataProducer) {
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

    public Map<String, String> getOptionsMap() {
        return optionsMap;
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
            return new ViewResponseBuilder().addTitleElement("question", viewName).addDropdownList(getDropdownElementName(), optionsMap).build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomParseException();
        }
    }

    private void saveOptionsMap(Response response) throws IOException {
        optionsMap = objectMapper.readValue(response.getRawData(), Map.class);

    }

    @Override
    public void getResult(Map<String, String> decisionArgs, List<PickResult> picks) {
        Optional<Map.Entry<String, String>> first = decisionArgs.entrySet().stream()
                .filter((entry) -> optionsMap.containsKey(entry.getKey().substring(entry.getKey().lastIndexOf('.') + 1))).findFirst();

        if (first.isPresent()){
            String key = first.get().getKey().substring(first.get().getKey().lastIndexOf('.') + 1);
            picks.add(new PickResult(getName(), key));
            picks.add(new PickResult(getName()+".Value", optionsMap.get(key)));
        }
    }

    private String getDropdownElementName(){
        return name+"-dropdown-list";
    }
}
