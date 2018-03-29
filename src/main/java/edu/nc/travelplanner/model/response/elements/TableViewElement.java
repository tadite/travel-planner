package edu.nc.travelplanner.model.response.elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.action.tableUtil.Row;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableViewElement implements ViewElement{
    private final List<String> links;
    private Boolean multiPick;
    private Boolean canPick;
    private String id;
    private final List<Row> rows;
    private final List<Row> columnDefs;

    public TableViewElement(String id, List<Row> rows, List<Row> columnDefs, List<String> links, Boolean multiPick, Boolean canPick) {
        this.id = id;
        this.rows = rows;
        this.columnDefs = columnDefs;
        this.links=links;
        this.multiPick = multiPick;
        this.canPick = canPick;
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
            this.put("links", links);
            if (canPick)
                this.put("multiPick", multiPick);
        }};
    }
}
