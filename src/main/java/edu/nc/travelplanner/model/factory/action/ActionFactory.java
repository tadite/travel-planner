package edu.nc.travelplanner.model.factory.action;

import edu.nc.travelplanner.model.action.Action;

public interface ActionFactory {
    Action createAction(String name) throws ActionParseException;
}
