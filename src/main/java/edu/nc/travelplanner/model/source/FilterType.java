package edu.nc.travelplanner.model.source;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FilterType {
    @JsonProperty("json_path")
    JSON_PATH,
    @JsonProperty("list_to_map")
    LIST_TO_MAP,
    @JsonProperty("regexp_replace")
    REGEXP_REPLACE,
    @JsonProperty("substring")
    SUBSTRING
}
