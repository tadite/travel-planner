package edu.nc.travelplanner.model.factory;

import java.io.IOException;

public interface ActionJsonReader {
    String getActionJson(String name) throws IOException;
}
