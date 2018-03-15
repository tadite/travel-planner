package edu.nc.travelplanner.model.source.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.FilterType;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListToMapMultipleJsonResponseFilter implements ResponseFilter {

    private String keyName;
    private List<String> valueNames = new LinkedList<>();

    private FilterType type = FilterType.LIST_TO_MAP_MULTIPLE;

    ObjectMapper mapper = new ObjectMapper();

    public ListToMapMultipleJsonResponseFilter() {
    }

    public ListToMapMultipleJsonResponseFilter(String keyName,List<String> valueNames) {
        this.keyName = keyName;
        this.valueNames = valueNames;
    }

    @Override
    public Response filter(Response sourceResult) {
        try {

            JsonNode node = mapper.readTree(sourceResult.getRawData());
            JsonParser parser = mapper.treeAsTokens(node);
            Map<String, Object>[] clients = parser.readValueAs(new TypeReference<Map<String, Object>[]>() {
            });
            Map<String, String> result = new HashMap<String, String>();
            for (Map<String, Object> map : clients) {
                String valueStr = "";
                List<String> collect = valueNames.stream().filter(name -> map.containsKey(name)).map(valueName -> map.get(valueName).toString()).collect(Collectors.toList());
                result.put(map.get(keyName).toString(), String.join(", ", collect));
            }

            sourceResult.setRawData(mapper.writeValueAsString(result));
            return sourceResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
