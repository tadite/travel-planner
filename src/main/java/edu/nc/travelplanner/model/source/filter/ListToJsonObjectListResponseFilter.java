package edu.nc.travelplanner.model.source.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.FilterType;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ListToJsonObjectListResponseFilter implements ResponseFilter {

    private String keyName;
    private List<String> valueNames = new LinkedList<>();

    private FilterType type = FilterType.LIST_TO_OBJECT_LIST;

    ObjectMapper mapper = new ObjectMapper();

    public ListToJsonObjectListResponseFilter() {
    }

    public ListToJsonObjectListResponseFilter(String keyName, List<String> valueNames) {
        this.keyName = keyName;
        this.valueNames = valueNames;
    }

    @Override
    public Response filter(Response sourceResult) {
        sourceResult.setRawData(filter(sourceResult.getRawData()));
        return sourceResult;
    }

    @Override
    public String filter(String sourceResult) {
        try {

            JsonNode node = mapper.readTree(sourceResult);
            JsonParser parser = mapper.treeAsTokens(node);

            Map<String, Object>[] jsonObjectsInArray = parser.readValueAs(new TypeReference<Map<String, Object>[]>() {
            });
            List<Map<String, String>> result = new LinkedList<>();

            String[] keyPropertyPath = keyName.split("\\.");

            for (Map<String, Object> jsonObj : jsonObjectsInArray) {
                Map<String, String> properties = new LinkedHashMap<>();

                properties.put("id", getValueByPropertyPath(jsonObj, keyPropertyPath));
                valueNames.stream()
                        .forEach(str ->
                                properties.put(str, getValueByPropertyPath(jsonObj, str.split("\\.")))
                        );

                result.add(properties);
            }

            return mapper.writeValueAsString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getValueByPropertyPath(Map<String, Object> jsonObj, String[] propertyPathArray) {
        Object currentPropertyValue = jsonObj;
        for (String nextProperty : propertyPathArray) {
            currentPropertyValue = ((Map<String, Object>) currentPropertyValue).get(nextProperty);
        }
        return currentPropertyValue.toString();
    }

    public FilterType getType() {
        return type;
    }

    public String getKeyName() {
        return keyName;
    }

    public List<String> getValueNames() {
        return valueNames;
    }
}
