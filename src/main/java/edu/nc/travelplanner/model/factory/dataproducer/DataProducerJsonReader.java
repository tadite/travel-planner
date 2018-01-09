package edu.nc.travelplanner.model.factory.dataproducer;

import java.io.IOException;

public interface DataProducerJsonReader {
    String getDataProducerJson(String name) throws IOException;
}
