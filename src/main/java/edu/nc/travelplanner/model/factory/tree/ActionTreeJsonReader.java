package edu.nc.travelplanner.model.factory.tree;

import java.io.IOException;

public interface ActionTreeJsonReader {
    String getActionTreeJson(String name) throws IOException;
}
