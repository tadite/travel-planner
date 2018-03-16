package edu.nc.travelplanner.model.source.filter;

import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.FilterType;

public class SplitAndGetByIndexResponseFilter implements ResponseFilter {
    private FilterType type = FilterType.SPLIT_AND_GET_BY_INDEX;
    private String split;
    private String index;

    public SplitAndGetByIndexResponseFilter() {
    }

    public SplitAndGetByIndexResponseFilter(String split, String index) {
        this.split = split;
        this.index = index;
    }

    @Override
    public Response filter(Response sourceResult) {
        sourceResult.setRawData(filter(sourceResult.getRawData()));
        return sourceResult;
    }

    @Override
    public String filter(String sourceResult) {
        try {
            return sourceResult.split(split)[getIndexValue()];
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    private Integer getIndexValue(){
        return Integer.valueOf(index);
    }
}
