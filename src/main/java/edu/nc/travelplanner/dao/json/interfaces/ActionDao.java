package edu.nc.travelplanner.dao.json.interfaces;

import edu.nc.travelplanner.dao.json.ActionApiDto;
import edu.nc.travelplanner.dao.json.SourceApiDto;

import java.util.List;

public interface ActionDao {
    ActionApiDto save(ActionApiDto dto) throws UnsupportedOperationException;
    ActionApiDto delete(String name) throws UnsupportedOperationException;
    ActionApiDto get(String name) throws UnsupportedOperationException;
    List<ActionApiDto> getAll() throws UnsupportedOperationException;
}
