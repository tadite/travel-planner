package edu.nc.travelplanner.model.factory.dataproducer;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.nc.travelplanner.model.source.parametermapper.ParameterMapper;

import java.util.List;

public class DataProducerDto {
    private String name;
    private List<ResponseFilterDto> filters;
    @JsonProperty("mappers")
    private List<ParameterMapperDto> parameterMappers;
    @JsonProperty("source")
    private String sourceName;

    public DataProducerDto() {
    }

    public DataProducerDto(String name, List<ResponseFilterDto> filters, String sourceName, List<ParameterMapperDto> parameterMappers) {
        this.name = name;
        this.filters = filters;
        this.sourceName = sourceName;
        this.parameterMappers = parameterMappers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResponseFilterDto> getFilters() {
        return filters;
    }

    public void setFilters(List<ResponseFilterDto> filters) {
        this.filters = filters;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public List<ParameterMapperDto> getParameterMappers() {
        return parameterMappers;
    }

    public void setParameterMappers(List<ParameterMapperDto> parameterMappers) {
        this.parameterMappers = parameterMappers;
    }
}
