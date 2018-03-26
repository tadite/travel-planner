package edu.nc.travelplanner.model.resultsMapper;

import edu.nc.travelplanner.dto.afterPickTree.TravelDto;
import edu.nc.travelplanner.model.action.PickResult;

import java.util.List;

public interface ResultsMapper {
    TravelDto map(List<PickResult> pickResults);
}
