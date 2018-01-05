package edu.nc.travelplanner.model.source.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ListToMapJsonResponseFilter implements ResponseFilter {

    private String keyName;
    private String valueName;

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

            JsonNode node = mapper.readTree(sourceResult.getRawData());
            JsonParser parser = mapper.treeAsTokens(node);
            Map<String, Object>[] clients = parser.readValueAs(new TypeReference<Map<String, Object>[]>() {
            });
            Map<String, String> result = new HashMap<String, String>();
            for (Map<String, Object> map : clients) {
                result.put(map.get(keyName).toString(), map.get(valueName).toString());
            }

            sourceResult.setRawData(mapper.writeValueAsString(result));
            return sourceResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}