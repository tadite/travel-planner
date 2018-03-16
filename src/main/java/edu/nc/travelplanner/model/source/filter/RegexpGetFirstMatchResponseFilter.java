package edu.nc.travelplanner.model.source.filter;

import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.FilterType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpGetFirstMatchResponseFilter implements ResponseFilter {
    private FilterType type = FilterType.REGEXP_FIRST_MATCH;
    private String regex;

    public RegexpGetFirstMatchResponseFilter() {
    }

    public RegexpGetFirstMatchResponseFilter(String regex) {
        this.regex = regex;
    }

    @Override
    public Response filter(Response sourceResult) {
        sourceResult.setRawData(filter(sourceResult.getRawData()));
        return sourceResult;
    }

    @Override
    public String filter(String sourceResult) {
        Matcher matcher = Pattern.compile(regex).matcher(sourceResult);
        return matcher.group(1);
    }
}
