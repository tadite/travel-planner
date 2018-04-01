package edu.nc.travelplanner.dao.json.interfaces;

import edu.nc.travelplanner.dao.json.DataProducerApiDto;
import edu.nc.travelplanner.dao.json.JumpApiDto;
import edu.nc.travelplanner.dao.json.JumpGraphDto;
import edu.nc.travelplanner.dao.json.JumpInsertDto;
import edu.nc.travelplanner.model.factory.tree.JumpDto;

import java.util.List;

public interface JumpDao {
    JumpDto save(JumpDto dto) throws UnsupportedOperationException;
    JumpDto insert(JumpInsertDto dto) throws UnsupportedOperationException;
    JumpDto delete(String from, String to) throws UnsupportedOperationException;
    JumpDto update(String from, String to, JumpDto dto) throws UnsupportedOperationException;
    JumpDto get(String from, String to) throws UnsupportedOperationException;
    List<JumpDto> getAll() throws UnsupportedOperationException;
    JumpGraphDto getJumpGraphDto() throws UnsupportedOperationException;
}
