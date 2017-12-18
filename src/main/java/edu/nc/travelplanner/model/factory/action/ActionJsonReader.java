package edu.nc.travelplanner.model.factory.action;

import java.io.IOException;

public interface ActionJsonReader {
    String getActionJson(String name) throws IOException;
}
