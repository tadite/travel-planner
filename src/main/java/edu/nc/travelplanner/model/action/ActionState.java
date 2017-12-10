package edu.nc.travelplanner.model.action;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ActionState {
    @JsonProperty("present")
    PRESENTATION,
    @JsonProperty("decision")
    DECISION
}
