package edu.nc.travelplanner.model.source.dataproducer;

import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;

public interface DataProducer {
    Response send() throws DataProducerParseException;
}
