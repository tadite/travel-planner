package edu.nc.travelplanner.model.action.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.exception.DataProducerSendException;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.IntegrationAction;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.action.tableUtil.Column;
import edu.nc.travelplanner.model.action.tableUtil.Row;
import edu.nc.travelplanner.model.action.tableUtil.TablePickResult;
import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;
import edu.nc.travelplanner.model.source.dataproducer.DataProducer;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class TableIntegrationAction implements IntegrationAction {

    private List<Row> rows = new LinkedList<>();
    private List<Row> fullRows = new LinkedList<>();

    private List<String> links = new LinkedList<>();
    private LinkedHashMap<String, String> columnDefs = new LinkedHashMap<>();
    private String name;
    private String viewName;
    private ActionType type = ActionType.TABLE_INTEGRATION;
    private DataProducer dataProducer;
    private Boolean multiPick = false;
    private Boolean canPick = true;

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
    public Response executePresentation(ActionArgs args, List<PickResult> pickResults) throws DataProducerSendException {

        try {
            Response response = dataProducer.send(pickResults);
            parseTable(response);

            return new ViewResponseBuilder().addTitleElement("question", viewName).addTable(getTableId(), rows, columnDefs, links, multiPick, canPick).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTableId() {
        return name;
    }

    private void parseTable(Response response) throws IOException {
        rows.clear();

        List<LinkedHashMap<String, String>> jsonObjs = objectMapper.readValue(response.getRawData(), List.class);
        int id = 99;
        for (LinkedHashMap<String, String> jsonObj : jsonObjs) {
            String idVal = String.valueOf(id++);

            Row currentFullRow = new Row();
            currentFullRow.addColumn(new Column("id", idVal));
            jsonObj.entrySet()
                    .forEach(entry -> {
                        if (entry.getKey() != "id")
                            currentFullRow.addColumn(new Column(entry.getKey(), entry.getValue()));
                    });
            fullRows.add(currentFullRow);

            Row currentRow = new Row();
            currentRow.addColumn(new Column("id", idVal));
            columnDefs.forEach((key, value) -> {
                currentRow.addColumn(new Column(
                        key,
                        jsonObj.getOrDefault(key, null)));
            });
            rows.add(currentRow);
        }
    }

    @Override
    public void getResult(Map<String, String> decisionArgs, List<PickResult> picks) {
        if (multiPick)
            saveMultipleResults(decisionArgs, picks);
        else
            saveOneResult(decisionArgs, picks);
    }

    private void saveMultipleResults(Map<String, String> decisionArgs, List<PickResult> picks) {
        List<TablePickResult> tablePickResults = new LinkedList<>();
        getArgsStreamForThisAction(decisionArgs)
                .forEach(arg1 -> {
                    String pickedId = arg1.getKey();

                    fullRows.stream().filter(
                            row -> row.getColumns()
                                    .stream()
                                    .anyMatch(column -> column.getName().equals("id") && column.getValue().equals(pickedId))
                    )
                            .forEach(row -> tablePickResults.add(
                                    new TablePickResult(row,
                                            new LinkedList<>(links),
                                            new LinkedHashMap<>(columnDefs))));
                });

        picks.add(new PickResult(getName(), tablePickResults, tablePickResults.getClass()));
    }

    private void saveOneResult(Map<String, String> decisionArgs, List<PickResult> picks) {
        getArgsStreamForThisAction(decisionArgs)
                .findFirst()
                .ifPresent(arg1 -> {
                    String pickedId = arg1.getKey();

                    fullRows.stream().filter(
                            row -> row.getColumns()
                                    .stream()
                                    .anyMatch(column -> column.getName().equals("id") && column.getValue().equals(pickedId))
                    )
                            .findFirst()
                            .ifPresent(row -> picks.add(new PickResult(getName(), new TablePickResult(row,
                                    new LinkedList<>(links),
                                    new LinkedHashMap<>(columnDefs)), TablePickResult.class)));
                });
    }

    private Stream<Map.Entry<String, String>> getArgsStreamForThisAction(Map<String, String> decisionArgs) {
        return decisionArgs.entrySet()
                .stream()
                .filter(arg -> {
                            if (arg.getValue() == null)
                                return false;
                            String value = arg.getValue().substring(arg.getValue().lastIndexOf('.') + 1,
                                    arg.getValue().length());
                            return value.equals(this.getName()) || value.equals("checked");
                        }
                );
    }

    public DataProducer getDataProducer() {
        return dataProducer;
    }

}
