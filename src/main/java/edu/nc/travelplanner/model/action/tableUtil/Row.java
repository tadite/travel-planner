package edu.nc.travelplanner.model.action.tableUtil;

import java.util.LinkedList;
import java.util.List;

public class Row{
    private List<Column> columns = new LinkedList<>();

    public void addColumn(Column column){
        this.columns.add(column);
    }

    public List<Column> getColumns() {
        return columns;
    }
}
