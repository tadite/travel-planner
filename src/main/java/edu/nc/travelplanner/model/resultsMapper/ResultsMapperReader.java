package edu.nc.travelplanner.model.resultsMapper;

import java.io.IOException;

public interface ResultsMapperReader {
    ResultsMapper read(String treeName) throws IOException;
}
