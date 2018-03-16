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
import java.util.stream.Stream;

public class ListToMapMultipleJsonResponseFilter implements ResponseFilter {

    private String keyName;
    private List<String> valueNames = new LinkedList<>();

    private FilterType type = FilterType.LIST_TO_MAP_MULTIPLE;

    ObjectMapper mapper = new ObjectMapper();

    public ListToMapMultipleJsonResponseFilter() {
    }

    public ListToMapMultipleJsonResponseFilter(String keyName, List<String> valueNames) {
        this.keyName = keyName;
        this.valueNames = valueNames;
    }

    @Override
    public Response filter(Response sourceResult) {
        try {

            JsonNode node = mapper.readTree(sourceResult.getRawData());
            JsonParser parser = mapper.treeAsTokens(node);

            Map<String, Object>[] jsonObjectsInArray = parser.readValueAs(new TypeReference<Map<String, Object>[]>() {
            });
            Map<String, String> result = new HashMap<String, String>();

            String[] keyPropertyPath = keyName.split("\\.");

            for (Map<String, Object> jsonObj : jsonObjectsInArray) {
                List<String> collect = valueNames.stream()
                        .map(str -> {
                            return getValueByPropertyPath(jsonObj, str.split("\\."));
                        })
                        .collect(Collectors.toList());

                result.put(getValueByPropertyPath(jsonObj, keyPropertyPath), String.join(", ", collect));
            }

            sourceResult.setRawData(mapper.writeValueAsString(result));
            return sourceResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getValueByPropertyPath(Map<String, Object> jsonObj, String[] propertyPathArray) {
        Object currentPropertyValue = jsonObj;
        for (String nextProperty : propertyPathArray) {
            currentPropertyValue = ((Map<String, Object>)currentPropertyValue).get(nextProperty);
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
