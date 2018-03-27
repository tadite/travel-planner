package edu.nc.travelplanner.model.action.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.exception.CustomParseException;
import edu.nc.travelplanner.exception.DataProducerSendException;
import edu.nc.travelplanner.exception.NotEnoughParamsException;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.IntegrationAction;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.action.tableUtil.Column;
import edu.nc.travelplanner.model.action.tableUtil.Row;
import edu.nc.travelplanner.model.action.tableUtil.ArrayTableDef;
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
    private List<Map<String, Object>> subtables = new LinkedList<>();
    private List<Map<String, Object>> arrayTables = new LinkedList<>();

    private List<ArrayTableDef> subtableDefs;
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
    public Response executePresentation(ActionArgs args, List<PickResult> pickResults) throws DataProducerSendException, CustomParseException, NotEnoughParamsException {

        try {
            Response response = dataProducer.send(pickResults);
            parseSubtableDefs(response);
            parseTable(response);

            return new ViewResponseBuilder().addTitleElement("question", viewName).addTable(getTableId(), rows, columnDefs, links, multiPick, canPick).build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomParseException();
        }
    }

    private void parseSubtableDefs(Response response) {
        List<ArrayTableDef> tempArrayTableDefs = new LinkedList<>();
        //subtables.forEach(cols -> subtableDefs.add(new ArrayTableDef()));
    }

    private String getTableId() {
        return name;
    }

    private void parseTable(Response response) throws IOException {
        rows.clear();
        LinkedHashMap<String, String> tempColumnDefs = new LinkedHashMap<>();

        List<LinkedHashMap<String, Object>> jsonObjs = objectMapper.readValue(response.getRawData(), List.class);
        int id = 99;
        for (LinkedHashMap<String, Object> jsonObj : jsonObjs) {
            String idVal = String.valueOf(id++);

            Row currentFullRow = new Row();
            currentFullRow.addColumn(new Column("id", idVal));
            jsonObj.entrySet()
                    .forEach(entry -> {
                        if (entry.getKey() != "id") {
                            Optional<Map<String, Object>> arrayTableOptional = arrayTables.stream().filter(tbl -> tbl.get("pick").equals(entry.getKey())).findFirst();
                            if (arrayTableOptional.isPresent()) {
                                String name = String.valueOf(arrayTableOptional.get().get("name"));
                                List<String> arrayTableColumnDefs = (List<String>) arrayTableOptional.get().get("columnDefs");

                                List<Map<String, Object>> arrayOfObjs = (List<Map<String, Object>>) entry.getValue();

                                List<Column> insideColumnsToAdd = new LinkedList<>();
                                for (Map<String, Object> obj : arrayOfObjs) {
                                    Integer i = 1;
                                    Integer finalI = i;
                                    Row tempRow = new Row();
                                    obj.forEach((key1, value1) -> tempRow.addColumn(new Column(key1, value1)));
                                    insideColumnsToAdd.add(new Column(entry.getKey() + " " + i, tempRow.getColumns()));
                                    tempColumnDefs.put(entry.getKey() + " " + i, name + " " + i);
                                    i++;
                                }
                                currentFullRow.addColumn(new Column(entry.getKey(), insideColumnsToAdd));
                            } else
                                currentFullRow.addColumn(new Column(entry.getKey(), entry.getValue()));
                        }
                    });
            fullRows.add(currentFullRow);

            Row currentRow = new Row();
            currentRow.addColumn(new Column("id", idVal));
            columnDefs.forEach((key, value) -> {
                Optional<Map<String, Object>> arrayTableOptional = arrayTables.stream().filter(tbl -> tbl.get("pick").equals(key)).findFirst();
                if (arrayTableOptional.isPresent()) {
                    String name = String.valueOf(arrayTableOptional.get().get("name"));

                    List<String> arrayTableColumnDefs = (List<String>) arrayTableOptional.get().get("columnDefs");

                    List<Map<String, Object>> arrayOfObjs = (List<Map<String, Object>>) jsonObj.get(key);

                    List<Column> insideColumnsToAdd = new LinkedList<>();
                    for (Map<String, Object> obj : arrayOfObjs) {
                        Integer i = 1;
                        Integer finalI = i;
                        Row tempRow = new Row();
                        obj.entrySet().forEach(objEntry -> {
                            if (arrayTableColumnDefs.contains(objEntry.getKey()))
                                tempRow.addColumn(new Column(objEntry.getKey(), objEntry.getValue()));
                        });
                        insideColumnsToAdd.add(new Column(key + " " + i, tempRow.getColumns()));

                        tempColumnDefs.put(key + " " + i, name + " " + i);
                        i++;
                    }
                    currentRow.addColumn(new Column(key, insideColumnsToAdd));
                } else
                    currentRow.addColumn(new Column(key, jsonObj.getOrDefault(key, null)));

            });
            rows.add(currentRow);
        }

        this.columnDefs.putAll(tempColumnDefs);
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
