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
    public String getRawData() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(elements);
    }

    public void addElement(ViewElement element){
        elements.add(element);
    }
}
