package edu.nc.travelplanner.dao.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.factory.action.ActionDto;

import java.util.Map;

public class ActionApiDto {

    private String name;
    private ActionType type;
    private Map<String, Object> parameters;
    @JsonProperty("dataProducer")
    private String dataProducerName;

    public ActionApiDto(){}

    public ActionApiDto(String name, ActionType type, Map<String, Object> parameters, String dataProducerName) {
        this.name = name;
        this.type = type;
        this.parameters = parameters;
        this.dataProducerName = dataProducerName;
    }

    public static ActionApiDto fromActionDto(ActionDto actionDto){
        ActionApiDto actionApiDto = new ActionApiDto();
        try {
            actionApiDto.setDataProducerName(actionDto.getDataProducerName());
            actionApiDto.setName(actionDto.getName());
            actionApiDto.setParameters(actionDto.getParameters());
            actionApiDto.setType(actionDto.getType());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return actionApiDto;
    }

    public ActionDto toActionDto(){
        ActionDto actionDto = new ActionDto();
        try {
            actionDto.setDataProducerName(this.getDataProducerName());
            actionDto.setName(this.getName());
            actionDto.setParameters(this.getParameters());
            actionDto.setType(this.getType());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return actionDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getDataProducerName() {
        return dataProducerName;
    }

    public void setDataProducerName(String dataProducerName) {
        this.dataProducerName = dataProducerName;
    }
}
