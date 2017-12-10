package edu.nc.travelplanner.model.tree;

public interface ActionTreeFactory {
    ActionTree createByName(String name);
}
