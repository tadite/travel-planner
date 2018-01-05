package edu.nc.travelplanner.model.source.filter;

import edu.nc.travelplanner.model.response.Response;

public class SubstringResponseFilter implements ResponseFilter{
    String firstIndexStr;
    String lastIndexStr;

    public SubstringResponseFilter(String firstIndexStr, String lastIndexStr) {
        this.firstIndexStr = firstIndexStr;
        this.lastIndexStr = lastIndexStr;
    }

    @Override
    public Response filter(Response sourceResult) {
        sourceResult.setRawData(sourceResult.getRawData()
                .substring(sourceResult.getRawData().indexOf(firstIndexStr)+1,
                        sourceResult.getRawData().indexOf(lastIndexStr)));
        return sourceResult;
    }
}
