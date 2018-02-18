package edu.nc.travelplanner.model.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import org.springframework.beans.factory.annotation.Autowired;

public class TravelResultResponse implements Response {

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    private TravelAfterPickTreeDto travelAfterPickTreeDto;

    public TravelResultResponse(TravelAfterPickTreeDto travelAfterPickTreeDto) {
        this.travelAfterPickTreeDto = travelAfterPickTreeDto;
    }

    @Override
    public String getRawData() {
        try {
            return objectMapper.writeValueAsString(travelAfterPickTreeDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void setRawData(String rawData) {

    }
}
