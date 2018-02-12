package edu.nc.travelplanner.dao.json.interfaces;

import edu.nc.travelplanner.dao.json.SourceApiDto;
import edu.nc.travelplanner.model.factory.source.SourceDto;

import java.util.List;

public interface SourceDao {
    SourceApiDto save(SourceApiDto dto) throws UnsupportedOperationException;
    SourceApiDto delete(String name) throws UnsupportedOperationException;
    SourceApiDto get(String name) throws UnsupportedOperationException;
    List<SourceApiDto> getAll() throws UnsupportedOperationException;
}

