package edu.nc.travelplanner.model.source.dataproducer;

import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;

import java.util.List;
import java.util.Map;

public interface DataProducer {
    Response send(List<PickResult> pickResults) throws DataProducerParseException;
}
