package edu.nc.travelplanner.model.source.filter;

import edu.nc.travelplanner.model.response.Response;

import java.text.ParseException;
import java.util.Map;

public interface ResponseFilter {
    Response filter(Response sourceResult);
    String filter(String sourceResult, Map<String, String> results);
}

