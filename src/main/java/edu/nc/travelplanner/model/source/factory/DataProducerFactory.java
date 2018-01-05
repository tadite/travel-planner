package edu.nc.travelplanner.model.source.factory;

import edu.nc.travelplanner.model.source.DataProducer;

public interface DataProducerFactory {
    DataProducer createDataProducer(String name) throws DataProducerParseException;
}
