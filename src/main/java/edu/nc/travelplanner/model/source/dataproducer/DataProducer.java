package edu.nc.travelplanner.model.source.dataproducer;

import edu.nc.travelplanner.exception.DataProducerSendException;
import edu.nc.travelplanner.exception.NotEnoughParamsException;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.Response;

import java.util.List;

public interface DataProducer {
    Response send(List<PickResult> pickResults) throws DataProducerSendException, NotEnoughParamsException;
}
