package edu.nc.travelplanner.model.action.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
    private List<String> subtables = new LinkedList<>();
    private List<Map<String, Object>> arrayTables = new LinkedList<>();

    private List<ArrayTableDef> subtableDefs;
    private LinkedHashMap<String, String> columnDefs = new LinkedHashMap<>();
    private LinkedHashMap<String, String> otherColumnDefs = new LinkedHashMap<>();
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
            parseTable(response);
            List<Row> rowsToReturn = this.rows;

            Row columnDefsRow = new Row();

            List<Row> columnDefRows = new LinkedList<>();
            LinkedHashMap<String, String> colDefs = this.columnDefs;
            Boolean oneLine = true;
            if (subtables != null && subtables.size() > 0) {
                oneLine = false;
                rowsToReturn = parseSubTables();
                for (Row row : rowsToReturn) {
                    columnDefRows.add(getNewColumnDefsRow(row));
                }
            } else {
                Row finalColumnDefsRow = columnDefsRow;
                columnDefs.forEach((key, value) -> {
                    if (value != "") finalColumnDefsRow.addColumn(new Column(key, value));
                });

                rows.forEach(row -> columnDefRows.add(finalColumnDefsRow));
            }

            return new ViewResponseBuilder().addTitleElement("question", viewName).addTable(getTableId(), rowsToReturn, columnDefRows, links, multiPick, canPick, oneLine).build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomParseException();
        }
    }

    private Row getNewColumnDefsRow(Row row) {
        Row newColumnsDefsRow = new Row();
        for (Column col : row.getColumns()) {
            if (columnDefs.containsKey(col.getName()))
                newColumnsDefsRow.addColumn(new Column(col.getName(), columnDefs.get(col.getName())));
            else if (otherColumnDefs.containsKey(col.getName()))
                newColumnsDefsRow.addColumn(new Column(col.getName(), otherColumnDefs.get(col.getName())));
            else if (col.getName().equals("separator"))
                newColumnsDefsRow.addColumn(separatorColumn);
            else if (col.getName().equals("booking"))
                newColumnsDefsRow.addColumn(separatorColumn);
        }
        return newColumnsDefsRow;
    }

    Column separatorColumn = new Column("separator", null);

    private List<Row> parseSubTables() {
        List<Row> subtabledRows = new LinkedList<>();
        List<String> currentSubtable = null;
        Map<String, Object> currentArrayTable = null;
        for (Row row : rows) {
            Row newRow = new Row();
            int count = 0;
            for (Column col : row.getColumns()) {
                if (subtables.stream().anyMatch(subtable -> col.getName().equals(subtable)))
                    newRow.addColumn(separatorColumn);
                newRow.addColumn(col);
            }

            subtabledRows.add(newRow);
        }
        return subtabledRows;
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

            final int[] i = {1};
            Row currentFullRow = new Row();
            currentFullRow.addColumn(new Column("id", idVal));
            jsonObj.entrySet()
                    .forEach(entry -> {
                        if (entry.getKey() != "id") {
                            Optional<Map<String, Object>> arrayTableOptional = arrayTables.stream().filter(tbl -> tbl.get("pick").equals(entry.getKey())).findFirst();
                            if (arrayTableOptional.isPresent()) {
                                i[0] = 1;
                                String name = String.valueOf(arrayTableOptional.get().get("name"));
                                List<String> arrayTableColumnDefs = (List<String>) arrayTableOptional.get().get("columnDefs");

                                if (entry.getValue() != null && !entry.getValue().equals("null") && entry.getValue() instanceof List) {
                                    List<Map<String, Object>> arrayOfObjs = (List<Map<String, Object>>) entry.getValue();

                                    List<Column> insideColumnsToAdd = new LinkedList<>();
                                    for (Map<String, Object> obj : arrayOfObjs) {


                                        Row tempRow = new Row();
                                        obj.forEach((key1, value1) -> tempRow.addColumn(new Column(key1, value1)));
                                        insideColumnsToAdd.add(new Column(entry.getKey() + " " + i[0], tempRow.getColumns()));
                                        tempColumnDefs.put(entry.getKey() + " " + i[0], name + " " + i[0]);
                                        i[0]++;
                                    }
                                    insideColumnsToAdd.forEach(col -> currentFullRow.addColumn(col));
                                }
                                //currentFullRow.addColumn(new Column(entry.getKey(), insideColumnsToAdd));

                            } else
                                currentFullRow.addColumn(new Column(entry.getKey(), entry.getValue()));
                        }
                    });
            fullRows.add(currentFullRow);

            i[0] = 1;
            Row currentRow = new Row();
            currentRow.addColumn(new Column("id", idVal));
            columnDefs.forEach((key, value) -> {
                Optional<Map<String, Object>> arrayTableOptional = arrayTables.stream().filter(tbl -> tbl.get("pick").equals(key)).findFirst();
                if (arrayTableOptional.isPresent()) {
                    i[0] = 1;
                    String name = String.valueOf(arrayTableOptional.get().get("name"));

                    List<String> arrayTableColumnDefs = (List<String>) arrayTableOptional.get().get("columnDefs");

                    if (jsonObj.get(key) != null && !jsonObj.get(key).equals("null") && jsonObj.get(key) instanceof List) {
                        List<Map<String, Object>> arrayOfObjs = (List<Map<String, Object>>) jsonObj.get(key);

                        List<Column> insideColumnsToAdd = new LinkedList<>();
                        for (Map<String, Object> obj : arrayOfObjs) {

                            Row tempRow = new Row();
                            obj.entrySet().forEach(objEntry -> {
                                if (arrayTableColumnDefs.contains(objEntry.getKey()))
                                    tempRow.addColumn(new Column(objEntry.getKey(), objEntry.getValue()));
                            });
                            insideColumnsToAdd.add(new Column(key + " " + i[0], name + " №" + i[0]));
                            tempRow.getColumns().forEach(col -> insideColumnsToAdd.add(col));
                            tempRow.addColumn(separatorColumn);

                            tempColumnDefs.put(key + " " + i[0], " ");
                            i[0]++;
                        }
                        insideColumnsToAdd.forEach(col -> currentRow.addColumn(col));
                    }
                    //currentRow.addColumn(new Column(key, insideColumnsToAdd));
                } else
                    currentRow.addColumn(new Column(key, jsonObj.getOrDefault(key, null)));

            });
            rows.add(currentRow);
        }

        this.otherColumnDefs.putAll(tempColumnDefs);
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
