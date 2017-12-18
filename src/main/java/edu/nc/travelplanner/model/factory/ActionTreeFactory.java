package edu.nc.travelplanner.model.factory;

import edu.nc.travelplanner.model.tree.ActionTree;

public interface ActionTreeFactory {
    ActionTree createByName(String name);
}
