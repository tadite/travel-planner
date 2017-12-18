package edu.nc.travelplanner.model.factory.tree;

import edu.nc.travelplanner.model.tree.ActionTree;

public interface ActionTreeFactory {
    ActionTree createByName(String name) throws ActionTreeParseException;
}
