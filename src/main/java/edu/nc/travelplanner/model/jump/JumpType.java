package edu.nc.travelplanner.model.jump;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum JumpType {
    @JsonProperty("without-condition")
    WITHOUT_CONDITION,
    @JsonProperty("logic-condition-on-pick-result")
    LOGIC_CONDITION_ON_PICK_RESULT
}
