package edu.nc.travelplanner.model.response.elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.action.tableUtil.Row;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableViewElement implements ViewElement{
    private String id;
    private final List<Row> rows;
    private final Row columnDefs;

    public TableViewElement(String id, List<Row> rows, Row columnDefs) {
        this.id = id;
        this.rows = rows;
        this.columnDefs = columnDefs;
    }

    @Override
    public ViewElementType getType() {
        return ViewElementType.TABLE_PICKER;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Object getData() throws JsonProcessingException {
        return new LinkedHashMap<String, Object>(){{
            this.put("id", id);
            this.put("columnDefs", columnDefs);
            this.put("rows", rows);
        }};
    }
}