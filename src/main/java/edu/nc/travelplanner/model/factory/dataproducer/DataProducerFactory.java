package edu.nc.travelplanner.model.factory.dataproducer;

import edu.nc.travelplanner.model.source.dataproducer.DataProducer;

public interface DataProducerFactory {
    DataProducer createDataProducer(String name) throws DataProducerParseException;
}
