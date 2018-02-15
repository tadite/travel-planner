package edu.nc.travelplanner.model.source;

import com.google.common.base.Joiner;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpSource implements Source{
    private String name;
    private String description;
    private String url;
    private List<String> params = new LinkedList<>();

    private Map<String, String> paramValuesMap = new HashMap<>();

    private SourceType type = SourceType.HTTP;

    public static final String REGEX_START = Pattern.quote("{{");
    public static final String REGEX_END = Pattern.quote("}}");
    public static final Pattern PATTERN = Pattern.compile(REGEX_START + "(.*?)" + REGEX_END);

    public HttpSource() {
    }

    public HttpSource(String name, String description, String url, List<String> parameters) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.params = parameters;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String getUrlWithParameterValues() {
        if (paramValuesMap.size()==0)
            return url;

        String outputUrl = url;

        String paramsString = Joiner.on("&").withKeyValueSeparator("=").join(paramValuesMap.entrySet()
                .stream()
                .filter(entry -> params.contains(entry.getKey()))
                .collect(Collectors.toList()));


        return url+"?" + paramsString;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @Override
    public SourceType getType() {
        return type;
    }

    public List<String> getParams() {
        return params;
    }

    @Override
    public void addParameterValue(String name, String value) {
        paramValuesMap.put(name, value);
    }
}
