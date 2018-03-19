package edu.nc.travelplanner.model.source.filter;

import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.FilterType;

public class RegexpReplaceAllResponseFilter implements ResponseFilter {
    private FilterType type = FilterType.REGEXP_REPLACE;
    private String regex;
    private String replacement;

    public RegexpReplaceAllResponseFilter() {
    }

    public RegexpReplaceAllResponseFilter(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public Response filter(Response sourceResult) {
        sourceResult.setRawData(filter(sourceResult.getRawData()));
        return sourceResult;
    }

    @Override
    public String filter(String sourceResult) {
        return sourceResult.replaceAll(regex,replacement);
    }
}
