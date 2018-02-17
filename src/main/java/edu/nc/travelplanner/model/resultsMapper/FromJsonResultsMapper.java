package edu.nc.travelplanner.model.resultsMapper;

import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.model.action.PickResult;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class FromJsonResultsMapper implements ResultsMapper {



    @Value( "${travelplanner.maintree}" )
    private String treeName;

    @Override
    public TravelAfterPickTreeDto map(List<PickResult> pickResults) {
        return null;
    }
}
