package edu.nc.travelplanner.model.resultsMapper;

import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.model.action.PickResult;

import java.util.List;

public interface ResultsMapper {
    TravelAfterPickTreeDto map(List<PickResult> pickResults);
}
