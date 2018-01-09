package edu.nc.travelplanner.model.factory.source;

import java.io.IOException;

public interface SourceJsonReader {
    String getSourceJson(String name) throws IOException;
}
