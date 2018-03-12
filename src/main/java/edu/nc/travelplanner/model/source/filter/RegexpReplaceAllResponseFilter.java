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
        sourceResult.setRawData(sourceResult.getRawData().replaceAll(regex,replacement));
        return sourceResult;
    }
}
