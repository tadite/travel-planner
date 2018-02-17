package edu.nc.travelplanner.model.resultsMapper;

import java.io.IOException;

public interface FromJsonResultsMapperDtoReader {
    FromJsonResultsMapperDto read(String treeName) throws IOException;
}
