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
    public Response executePresentation(ActionArgs args, List<PickResult> pickResults) {

        try {
            Response response = dataProducer.send(pickResults);
            saveOptionsMap(response);
            return new ViewResponseBuilder().addTitleElement("question", viewName).addDropdownList(getDropdownElementName(), optionsMap).build();
        } catch (DataProducerParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveOptionsMap(Response response) throws IOException {
        optionsMap = objectMapper.readValue(response.getRawData(), Map.class);

    }

    @Override
    public String getResult(Map<String, String> decisionArgs) {
        Optional<Map.Entry<String, String>> first = decisionArgs.entrySet().stream()
                .filter((entry) -> optionsMap.containsKey(entry.getKey())).findFirst();
        if (first.isPresent())
            return first.get().getKey();
        return null;
    }

    private String getDropdownElementName(){
        return name+"-dropdown-list";
    }
}
