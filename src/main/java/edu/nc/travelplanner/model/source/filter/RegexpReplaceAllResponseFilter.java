package edu.nc.travelplanner.model.source.filter;

import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.FilterType;

import java.util.Map;

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
        sourceResult.setRawData(filter(sourceResult.getRawData(), null));
        return sourceResult;
    }

    @Override
    public String filter(String sourceResult, Map<String, String> results) {
        return sourceResult.replaceAll(regex,replacement);
    }
}
