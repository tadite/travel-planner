package edu.nc.travelplanner.model.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.dto.afterPickTree.TravelDto;
import org.springframework.beans.factory.annotation.Autowired;

public class TravelResultResponse implements Response {

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    private TravelDto travelDto;

    public TravelResultResponse(TravelDto travelDto) {
        this.travelDto = travelDto;
    }

    @Override
    public String getRawData() {
        try {
            return objectMapper.writeValueAsString(travelDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void setRawData(String rawData) {

    }
}
