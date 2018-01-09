package edu.nc.travelplanner.model.source.filter;

import edu.nc.travelplanner.model.response.Response;

public class RegexpReplaceAllResponseFilter implements ResponseFilter {
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
