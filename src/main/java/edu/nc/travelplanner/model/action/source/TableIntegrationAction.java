package edu.nc.travelplanner.model.action.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.IntegrationAction;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;
import edu.nc.travelplanner.model.response.EmptyResponse;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.response.ViewResponseBuilder;
import edu.nc.travelplanner.model.source.dataproducer.DataProducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TableIntegrationAction implements IntegrationAction {

    class Column{
        private String name;
        private String value;

        public Column(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class Row{
        private List<Column> columns = new LinkedList<>();

        public void addColumn(Column column){
            this.columns.add(column);
        }

        public List<Column> getColumns() {
            return columns;
        }
    }

    private List<Row> rows = new LinkedList<>();
    private List<String> paramsToCheck = new LinkedList<>();
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

            return new ViewResponseBuilder().addTitleElement("question", viewName).build();
        } catch (DataProducerParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseTable(Response response) {

    }

    @Override
    public Object getResult(Map<String, String> decisionArgs) {
        return null;
    }

    public DataProducer getDataProducer() {
        return dataProducer;
    }

}
