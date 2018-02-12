package edu.nc.travelplanner.dao.json.interfaces;

import edu.nc.travelplanner.dao.json.DataProducerApiDto;
import edu.nc.travelplanner.dao.json.SourceApiDto;

import java.util.List;

public interface DataProducerDao {
    DataProducerApiDto save(DataProducerApiDto dto) throws UnsupportedOperationException;
    DataProducerApiDto delete(String name) throws UnsupportedOperationException;
    DataProducerApiDto get(String name) throws UnsupportedOperationException;
    List<DataProducerApiDto> getAll() throws UnsupportedOperationException;
}
