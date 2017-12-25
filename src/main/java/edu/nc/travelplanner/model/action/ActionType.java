package edu.nc.travelplanner.model.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum ActionType {
    @JsonProperty("info")
    INFO,
    @JsonProperty("checklist")
    CHECKLIST,
    @JsonProperty("text_input")
    TEXT_INPUT,
    @JsonProperty("dropdown_input")
    DROPDOWN_INPUT,
    @JsonProperty("date_interval_input")
    DATE_INTERVAL_INPUT


}
