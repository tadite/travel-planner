package edu.nc.travelplanner.model.source;

import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.factory.DataProducerParseException;

public interface DataProducer {
    Response send() throws DataProducerParseException;
}
