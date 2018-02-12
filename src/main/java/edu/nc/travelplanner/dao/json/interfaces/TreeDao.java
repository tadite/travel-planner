package edu.nc.travelplanner.dao.json.interfaces;

import edu.nc.travelplanner.dao.json.TreeApiDto;

import java.util.List;

public interface TreeDao {
    TreeApiDto save(TreeApiDto dto) throws UnsupportedOperationException;
    TreeApiDto delete(String name) throws UnsupportedOperationException;
    TreeApiDto get(String name) throws UnsupportedOperationException;
    List<TreeApiDto> getAll() throws UnsupportedOperationException;
}
