package edu.nc.travelplanner.model.source.filter;

import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.FilterType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateFormatParserResponseFilter implements ResponseFilter {
    private FilterType type = FilterType.DATE_FORMAT_PARSE;
    private String fromFormat;
    private String toFormat;

    public DateFormatParserResponseFilter() {
    }

    public DateFormatParserResponseFilter(String fromFormat, String toFormat) {
        this.fromFormat = fromFormat;
        this.toFormat = toFormat;
    }

    @Override
    public Response filter(Response sourceResult) {

        sourceResult.setRawData(filter(sourceResult.getRawData(),null));

        return sourceResult;
    }

    @Override
    public String filter(String sourceResult, Map<String, Object> results) {

        String startTime = sourceResult;

        SimpleDateFormat input = new SimpleDateFormat(fromFormat);
        Date dateValue = null;
        try {
            dateValue = input.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat output = new SimpleDateFormat(toFormat);

        return output.format(dateValue);
    }
}
