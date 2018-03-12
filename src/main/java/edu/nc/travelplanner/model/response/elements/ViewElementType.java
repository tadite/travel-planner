package edu.nc.travelplanner.model.response.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ViewElementType {
    @JsonProperty("title")
    TITLE,
    @JsonProperty("text_input")
    TEXT_INPUT,
    @JsonProperty("checkbox")
    CHECKBOX,
    @JsonProperty("dropdown_list")
    DROPDOWN_TEXT_LIST,
    @JsonProperty("date_picker")
    DATE_PICKER,
    @JsonProperty("radiobox")
    RADIOBOX
}
