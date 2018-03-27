package edu.nc.travelplanner.model.action.tableUtil;

import java.util.LinkedList;
import java.util.List;


public class ArrayTableDef {
    private List<String> columnDefs;
    private String pick;
    private String name;
    private List<Row> rows = new LinkedList<>();
    private List<Row> fullRows = new LinkedList<>();

    public List<String> getColumnDefs() {
        return columnDefs;
    }

    public void setColumnDefs(List<String> columnDefs) {
        this.columnDefs = columnDefs;
    }

    public String getPick() {
        return pick;
    }

    public void setPick(String pick) {
        this.pick = pick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public List<Row> getFullRows() {
        return fullRows;
    }

    public void setFullRows(List<Row> fullRows) {
        this.fullRows = fullRows;
    }
}
