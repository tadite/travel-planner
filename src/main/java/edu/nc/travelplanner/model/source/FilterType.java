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
    SUBSTRING,
    @JsonProperty("list_to_map_multiple")
    LIST_TO_MAP_MULTIPLE,
    @JsonProperty("list_to_object_list")
    LIST_TO_OBJECT_LIST,
    @JsonProperty("regexp_first_match")
    REGEXP_FIRST_MATCH,
    @JsonProperty("split_and_get_by_index")
    SPLIT_AND_GET_BY_INDEX
}
