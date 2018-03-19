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
    DATE_INTERVAL_INPUT,
    @JsonProperty("checklist_integration")
    CHECKLIST_INTEGRATION,
    @JsonProperty("info_integration")
    INFO_INTEGRATION,
    @JsonProperty("dropdown_integration")
    DROPDOWN_INTEGRATION,
    @JsonProperty("radiolist")
    RADIOLIST,
    @JsonProperty("radiolist_integration")
    RADIOLIST_INTEGRATION,
    @JsonProperty("table_integration")
    TABLE_INTEGRATION,
    @JsonProperty("no_view_text_integration")
    NO_VIEW_TEXT_INTEGRAION

}
