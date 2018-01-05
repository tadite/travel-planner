package edu.nc.travelplanner.model.source.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import edu.nc.travelplanner.model.response.Response;

public class JsonPathResponseFilter implements ResponseFilter{
    private String expression;

    public JsonPathResponseFilter(String jsonPathExpression) {
        this.expression = jsonPathExpression;
    }

    @Override
    public Response filter(Response sourceResult)  {

        DocumentContext document = JsonPath.parse(sourceResult.getRawData());
        sourceResult.setRawData(document.read(expression).toString());
        return sourceResult;
    }

}
