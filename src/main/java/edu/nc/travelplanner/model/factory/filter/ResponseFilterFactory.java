package edu.nc.travelplanner.model.factory.filter;

import edu.nc.travelplanner.model.factory.dataproducer.ResponseFilterDto;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;

public interface ResponseFilterFactory {
    ResponseFilter create(ResponseFilterDto responseFilterDto) throws FilterParseException;
}
