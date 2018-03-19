package edu.nc.travelplanner.model.source.filter;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.FilterType;

public class GetValueIfContainsInArrayFromJsonResponseFilter implements ResponseFilter{

    private String jsonFilePath;
    private FilterType type = FilterType.GET_VALUE_IF_CONTAINS_IN_ARRAY_FROM_JSON_FILE;

    public GetValueIfContainsInArrayFromJsonResponseFilter() {
    }

    @Override
    public Response filter(Response sourceResult)  {
        sourceResult.setRawData(sourceResult.getRawData());
        return sourceResult;
    }

    @Override
    public String filter(String sourceResult) {



        return null;
    }

    public FilterType getType() {
        return type;
    }
}
