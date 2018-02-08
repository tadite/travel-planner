package edu.nc.travelplanner.model.factory.mapper;

import edu.nc.travelplanner.model.factory.dataproducer.ResponseFilterDto;
import edu.nc.travelplanner.model.factory.filter.FilterParseException;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import edu.nc.travelplanner.model.source.parametermapper.ParameterMapper;

import java.lang.reflect.Parameter;

public interface ParameterMapperFactory {
    ParameterMapper create(ParameterMapper responseFilterDto) throws FilterParseException;
}
