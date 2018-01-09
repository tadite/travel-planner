package edu.nc.travelplanner.model.factory.source;

import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;
import edu.nc.travelplanner.model.source.Source;
import edu.nc.travelplanner.model.source.dataproducer.DataProducer;

public interface SourceFactory {
    Source createSource(String name) throws SourceParseException;
}
