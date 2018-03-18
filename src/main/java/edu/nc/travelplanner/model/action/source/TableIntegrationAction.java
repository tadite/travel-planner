package edu.nc.travelplanner.model.action.source;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.IntegrationAction;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.action.tableUtil.Column;
import edu.nc.travelplanner.model.action.tableUtil.Row;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;
import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;
import edu.nc.travelplanner.model.source.dataproducer.DataProducer;

import java.io.IOException;
import java.util.*;

public class TableIntegrationAction implements IntegrationAction {

    private List<Row> rows = new LinkedList<>();
    private LinkedHashMap<String, String> columnDefs = new LinkedHashMap<>();
    private String name;
    private String viewName;
    private ActionType type = ActionType.TABLE_INTEGRATION;
    private DataProducer dataProducer;

    ObjectMapper objectMapper = new ObjectMapper();

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
    public Response executeDecision(ActionArgs args, List<PickResult> pickResult) {
        return new EmptyResponse();
    }

    @Override
    public Response executePresentation(ActionArgs args, List<PickResult> pickResults) {

        try {
            Response response = dataProducer.send(pickResults);
            parseTable(response);

            return new ViewResponseBuilder().addTitleElement("question", viewName).addTable(getTableId(), rows, columnDefs).build();
        } catch (DataProducerParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTableId() {
        return name+"-table";
    }

    private void parseTable(Response response) throws IOException {
        List<LinkedHashMap<String,String>> jsonObjs = objectMapper.readValue(response.getRawData(), List.class);
        for (LinkedHashMap<String, String> jsonObj : jsonObjs) {
            Row currentRow = new Row();
            currentRow.addColumn(new Column("id", jsonObj.getOrDefault("id",null)));
            columnDefs.forEach((key, value) -> {
                    currentRow.addColumn(new Column(
                            key,
                            jsonObj.getOrDefault(key,null)));
            });
            rows.add(currentRow);
        }
    }

    @Override
    public Object getResult(Map<String, String> decisionArgs) {
        return null;
    }

    public DataProducer getDataProducer() {
        return dataProducer;
    }

}
