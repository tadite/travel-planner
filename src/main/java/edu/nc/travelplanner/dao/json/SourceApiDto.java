package edu.nc.travelplanner.dao.json;

import edu.nc.travelplanner.model.factory.source.SourceDto;
import edu.nc.travelplanner.model.source.SourceType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SourceApiDto {
    private String name;
    private SourceType type;
    private String url;
    private String description;
    /**
     * Get request parameters list
     */
    private List<String> params = new LinkedList<>();

    public SourceApiDto() {
    }

    public SourceApiDto(String name, SourceType type, String url, String description, List<String> params) {
        this.name = name;
        this.type = type;
        this.url = url;
        this.description = description;
        this.params = params;
    }

    public static SourceApiDto fromSourceDto(SourceDto sourceDto){
        SourceApiDto sourceApiDto = new SourceApiDto();
        try {
            sourceApiDto.name = sourceDto.getName();
            sourceApiDto.type = sourceDto.getType();
            sourceApiDto.url = (String) sourceDto.getParameters().get("url");
            sourceApiDto.description = (String) sourceDto.getParameters().get("description");
            sourceApiDto.params = (List<String>) sourceDto.getParameters().get("params");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return sourceApiDto;
    }

    public SourceDto toSourceDto(){
        SourceDto sourceDto = new SourceDto();
        try {
            sourceDto.setName(this.getName());
            sourceDto.setType(this.getType());

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("description", this.getDescription());
            parameters.put("params", this.getParams());
            parameters.put("url", this.getUrl());

            sourceDto.setParameters(parameters);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return sourceDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SourceType getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }
}
