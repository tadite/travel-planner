package edu.nc.travelplanner.model.action.tableUtil;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class TablePickResult {

    private Row row;
    private List<String> links = new LinkedList<>();
    private LinkedHashMap<String, String> columnDefs = new LinkedHashMap<>();

    public TablePickResult(Row row, List<String> links, LinkedHashMap<String, String> columnDefs) {
        this.row = row;
        this.links = links;
        this.columnDefs = columnDefs;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
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
