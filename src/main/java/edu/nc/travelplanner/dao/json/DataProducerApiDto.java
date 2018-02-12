package edu.nc.travelplanner.dao.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerDto;
import edu.nc.travelplanner.model.factory.dataproducer.ParameterMapperDto;
import edu.nc.travelplanner.model.factory.dataproducer.ResponseFilterDto;
import edu.nc.travelplanner.model.factory.source.SourceDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProducerApiDto {
    private String name;
    private List<ResponseFilterDto> filters;
    @JsonProperty("mappers")
    private List<ParameterMapperDto> parameterMappers;
    @JsonProperty("source")
    private String sourceName;

    public DataProducerApiDto() {
    }

    public DataProducerApiDto(String name, List<ResponseFilterDto> filters, String sourceName, List<ParameterMapperDto> parameterMappers) {
        this.name = name;
        this.filters = filters;
        this.sourceName = sourceName;
        this.parameterMappers = parameterMappers;
    }

    public static DataProducerApiDto fromDataProducerDto(DataProducerDto dataProducerDto){
        DataProducerApiDto sourceApiDto = new DataProducerApiDto();
        try {
            sourceApiDto.setFilters(dataProducerDto.getFilters());
            sourceApiDto.setSourceName(dataProducerDto.getSourceName());
            sourceApiDto.setName(dataProducerDto.getName());
            sourceApiDto.setParameterMappers(dataProducerDto.getParameterMappers());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return sourceApiDto;
    }

    public DataProducerDto toDataProducerDto(){
        DataProducerDto dataProducerDto = new DataProducerDto();
        try {
            dataProducerDto.setParameterMappers(this.getParameterMappers());
            dataProducerDto.setName(this.getName());
            dataProducerDto.setSourceName(this.getSourceName());
            dataProducerDto.setFilters(this.getFilters());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return dataProducerDto;
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
