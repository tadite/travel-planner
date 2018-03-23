package edu.nc.travelplanner.model.action.tableUtil;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class TablePickResult {

    private List<Row> rows = new LinkedList<>();
    private List<String> links = new LinkedList<>();
    private LinkedHashMap<String, String> columnDefs = new LinkedHashMap<>();

    public TablePickResult(List<Row> rows, List<String> links, LinkedHashMap<String, String> columnDefs) {
        this.rows = rows;
        this.links = links;
        this.columnDefs = columnDefs;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public LinkedHashMap<String, String> getColumnDefs() {
        return columnDefs;
    }

    public void setColumnDefs(LinkedHashMap<String, String> columnDefs) {
        this.columnDefs = columnDefs;
    }
}
