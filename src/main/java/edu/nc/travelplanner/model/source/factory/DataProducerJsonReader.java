package edu.nc.travelplanner.model.source.factory;

import java.io.IOException;

public interface DataProducerJsonReader {
    String getDataProducerJson(String name) throws IOException;
}
