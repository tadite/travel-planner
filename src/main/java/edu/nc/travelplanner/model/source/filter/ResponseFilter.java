package edu.nc.travelplanner.model.source.filter;

import edu.nc.travelplanner.model.response.Response;

public interface ResponseFilter {
    Response filter(Response sourceResult);
    String filter(String sourceResult);
}

