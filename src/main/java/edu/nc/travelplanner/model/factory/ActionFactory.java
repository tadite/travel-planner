package edu.nc.travelplanner.model.factory;

import edu.nc.travelplanner.model.action.Action;

import java.io.IOException;

public interface ActionFactory {
    Action createAction(String name) throws ActionParseException;
}
