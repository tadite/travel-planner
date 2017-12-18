package edu.nc.travelplanner.model.factory.tree;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.factory.action.ActionParseException;
import edu.nc.travelplanner.model.tree.ActionTree;

public interface TreeFactory {
    ActionTree createActionTree(String name) throws ActionTreeParseException;
}
