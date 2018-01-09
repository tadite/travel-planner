package edu.nc.travelplanner.model.factory.dataproducer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DataProducerDto {
    private String name;
    private List<ResponseFilterDto> filters;
    @JsonProperty("source")
    private String sourceName;

    public DataProducerDto() {
    }

    public DataProducerDto(String name, List<ResponseFilterDto> filters, String sourceName) {
        this.name = name;
        this.filters = filters;
        this.sourceName = sourceName;
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
}
