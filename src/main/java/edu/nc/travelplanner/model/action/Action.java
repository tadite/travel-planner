package edu.nc.travelplanner.model.action;

import edu.nc.travelplanner.exception.CustomParseException;
import edu.nc.travelplanner.exception.DataProducerSendException;
import edu.nc.travelplanner.exception.NotEnoughParamsException;
import edu.nc.travelplanner.model.response.Response;

import java.util.List;
import java.util.Map;

public interface Action {
    String getName();
    String getViewName();
    ActionType getType();
    Response executeDecision(ActionArgs args, List<PickResult> pickResult) throws DataProducerSendException, CustomParseException;
    Response executePresentation(ActionArgs args, List<PickResult> pickResult) throws DataProducerSendException, CustomParseException, NotEnoughParamsException;
    void getResult(Map<String, String> decisionArgs, List<PickResult> picks);
}
