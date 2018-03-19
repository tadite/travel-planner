package edu.nc.travelplanner.model.source.filter;

import edu.nc.travelplanner.model.response.Response;

public class SubstringResponseFilter implements ResponseFilter{
    String firstIndexStr;
    String lastIndexStr;

    public SubstringResponseFilter() {
    }

    public SubstringResponseFilter(String firstIndexStr, String lastIndexStr) {
        this.firstIndexStr = firstIndexStr;
        this.lastIndexStr = lastIndexStr;
    }

    @Override
    public Response filter(Response sourceResult) {
        sourceResult.setRawData(filter(sourceResult.getRawData()));
        return sourceResult;
    }

    @Override
    public String filter(String sourceResult) {
        return sourceResult
                .substring(sourceResult.indexOf(firstIndexStr)+1,
                        sourceResult.indexOf(lastIndexStr));
    }
}
