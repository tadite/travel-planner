package edu.nc.travelplanner.model.source.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.FilterType;

public class JsonPathResponseFilter implements ResponseFilter{
    private String expression;
    private FilterType type = FilterType.JSON_PATH;

    public JsonPathResponseFilter() {
    }

    public JsonPathResponseFilter(String jsonPathExpression) {
        this.expression = jsonPathExpression;
    }

    @Override
    public Response filter(Response sourceResult)  {

        DocumentContext document = JsonPath.parse(sourceResult.getRawData());
        sourceResult.setRawData(document.read(expression).toString());
        return sourceResult;
    }

    public String getExpression() {
        return expression;
    }

    public FilterType getType() {
        return type;
    }
}
