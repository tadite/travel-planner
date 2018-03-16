package edu.nc.travelplanner.model.source.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.FilterType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListToMapJsonResponseFilter implements ResponseFilter {

    private String keyName;
    private String valueName;

    private FilterType type = FilterType.JSON_PATH;

    ObjectMapper mapper = new ObjectMapper();

    public ListToMapJsonResponseFilter() {
    }

    public ListToMapJsonResponseFilter(String keyName, String valueName) {
        this.keyName = keyName;
        this.valueName = valueName;
    }

    @Override
    public Response filter(Response sourceResult) {
        try {
            /*
            JsonNode node = mapper.readTree(sourceResult.getRawData());
            JsonParser parser = mapper.treeAsTokens(node);
            Map<String, Object>[] clients = parser.readValueAs(new TypeReference<Map<String, Object>[]>() {
            });

            Map<String, String> result = new HashMap<String, String>();
            for (Map<String, Object> map : clients) {
                result.put(map.get(keyName).toString(), map.get(valueName).toString());
            }
*/
            JsonNode node = mapper.readTree(sourceResult.getRawData());
            JsonParser parser = mapper.treeAsTokens(node);

            Map<String, Object>[] jsonObjectsInArray = parser.readValueAs(new TypeReference<Map<String, Object>[]>() {
            });
            Map<String, String> result = new HashMap<String, String>();

            String[] keyPropertyPath = keyName.split("\\.");
            String[] valuePropertyPath = valueName.split("\\.");

            for (Map<String, Object> jsonObj : jsonObjectsInArray) {

                result.put(getValueByPropertyPath(jsonObj, keyPropertyPath), getValueByPropertyPath(jsonObj, valuePropertyPath));
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

    public String getValueName() {
        return valueName;
    }
}