package edu.nc.travelplanner.model.source.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.factory.PathUtil;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.FilterType;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetValueListIfContainsInPropertyFromJsonArray implements ResponseFilter {


    private String jsonFilePath;

    private List<ParameterMapperEntry> mappers;

    private List<Map<String, String>> paramMappers;
    private String keyPath;

    private FilterType type = FilterType.GET_VALUELIST_IF_IN_PROPERTY_FROM_JSON_ARRAY;

    public GetValueListIfContainsInPropertyFromJsonArray() {
    }

    public GetValueListIfContainsInPropertyFromJsonArray(String jsonFilePath, List<ParameterMapperEntry> mappers, String keyPath) {
        this.jsonFilePath = jsonFilePath;
        this.mappers = mappers;
        this.keyPath = keyPath;
    }

    @Override
    public Response filter(Response sourceResult) {
        sourceResult.setRawData(sourceResult.getRawData());
        return sourceResult;
    }

    @Override
    public String filter(String sourceResult, Map<String, String> results) {
        createMappersIfNull();
        try {
            String result = sourceResult.toLowerCase();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(new File(PathUtil.getPathInUserDir(jsonFilePath)));
            JsonParser parser = mapper.treeAsTokens(node);

            Map<String, Object>[] jsonObjectsInArray = parser.readValueAs(new TypeReference<Map<String, Object>[]>() {
            });

            String[] keyPropertyPath = keyPath.split("__");
            mappers.forEach(resultMapper -> resultMapper.setPathArray(resultMapper.getPath().split("__")));

            for (Map<String, Object> jsonObj : jsonObjectsInArray) {
                String keyValue = (String) getValueByPropertyPath(jsonObj, keyPropertyPath);
                if (keyValue.toLowerCase().contains(result)) {
                    mappers.forEach(resultMapper ->
                            results.put(resultMapper.getName(),
                                    String.valueOf(getValueByPropertyPath(jsonObj, resultMapper.getPathArray()))));

                    return keyValue;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createMappersIfNull() {
        if (this.mappers == null)
            this.mappers = paramMappers.stream()
                    .map(map -> new ParameterMapperEntry(map.get("path"), map.get("name"))).collect(Collectors.toList());
    }

    public FilterType getType() {
        return type;
    }

    private Object getValueByPropertyPath(Map<String, Object> jsonObj, String[] propertyPathArray) {
        Object currentPropertyValue = jsonObj;
        for (String nextProperty : propertyPathArray) {
            currentPropertyValue = ((Map<String, Object>) currentPropertyValue).get(nextProperty);
        }
        return currentPropertyValue;
    }

    public List<Map<String, String>> getParamMappers() {
        return paramMappers;
    }

    public void setParamMappers(List<Map<String, String>> paramMappers) {
        this.paramMappers = paramMappers;
    }
}
