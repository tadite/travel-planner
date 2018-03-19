package edu.nc.travelplanner.model.source.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import edu.nc.travelplanner.model.factory.PathUtil;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.FilterType;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetValueIfContainsInArrayFromJsonResponseFilter implements ResponseFilter{

    private String jsonFilePath;
    private String valuePath;
    private String arrayPath;

    private FilterType type = FilterType.GET_VALUE_IF_CONTAINS_IN_ARRAY_FROM_JSON_FILE;

    public GetValueIfContainsInArrayFromJsonResponseFilter() {
    }

    public GetValueIfContainsInArrayFromJsonResponseFilter(String jsonFilePath, String valuePath, String arrayPath) {
        this.jsonFilePath = jsonFilePath;
        this.valuePath = valuePath;
        this.arrayPath = arrayPath;
    }

    @Override
    public Response filter(Response sourceResult)  {
        sourceResult.setRawData(sourceResult.getRawData());
        return sourceResult;
    }

    @Override
    public String filter(String sourceResult) {
        try {
            String result = sourceResult.toLowerCase();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(new File(PathUtil.getPathInUserDir(jsonFilePath)));
            JsonParser parser = mapper.treeAsTokens(node);

            Map<String, Object>[] jsonObjectsInArray = parser.readValueAs(new TypeReference<Map<String, Object>[]>() {
            });

            String[] valuePropertyPath = valuePath.split("__");
            String[] arrayPropertyPath = arrayPath.split("__");

            for (Map<String, Object> jsonObj : jsonObjectsInArray) {
                List<String> array = (List<String>) getValueByPropertyPath(jsonObj, arrayPropertyPath);
                if (array.contains(result))
                    return getValueByPropertyPath(jsonObj, valuePropertyPath).toString();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public FilterType getType() {
        return type;
    }

    private Object getValueByPropertyPath(Map<String, Object> jsonObj, String[] propertyPathArray) {
        Object currentPropertyValue = jsonObj;
        for (String nextProperty : propertyPathArray) {
            currentPropertyValue = ((Map<String, Object>)currentPropertyValue).get(nextProperty);
        }
        return currentPropertyValue;
    }
}
