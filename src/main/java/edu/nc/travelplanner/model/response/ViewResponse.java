package edu.nc.travelplanner.model.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.response.elements.ViewElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.LinkedList;
import java.util.List;

public class ViewResponse implements Response{

    private List<ViewElement> elements = new LinkedList<>();

    @Override
    public String getRawData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(elements);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setRawData(String rawData) {

    }

    public void addElement(ViewElement element){
        elements.add(element);
    }
}
