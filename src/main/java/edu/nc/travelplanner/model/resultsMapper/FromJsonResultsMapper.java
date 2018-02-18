package edu.nc.travelplanner.model.resultsMapper;

import edu.nc.travelplanner.dto.afterPickTree.CheckpointAfterPickTreeDto;
import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.table.Travel;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

public class FromJsonResultsMapper implements ResultsMapper {

    private Map<String, String> paramsToMap;

    public FromJsonResultsMapper(Map<String, String> paramsToMap) {
        this.paramsToMap = paramsToMap;
    }

    @Override
    public TravelAfterPickTreeDto map(List<PickResult> pickResults) {
        TravelAfterPickTreeDto travelAfterPickTreeDto = new TravelAfterPickTreeDto();

        CheckpointAfterPickTreeDto from = new CheckpointAfterPickTreeDto();
        if (paramsToMap.containsKey("from.cityId"))
            pickResults.stream()
                .filter(
                        pickResult -> pickResult.getKey().equals(paramsToMap.get("from.cityId"))
                ).findFirst().ifPresent(pickResult -> from.setCityId(Long.valueOf(pickResult.getValue().toString())));
        if (paramsToMap.containsKey("from.countryId"))
            pickResults.stream()
                    .filter(
                            pickResult -> pickResult.getKey().equals(paramsToMap.get("from.countryId"))
                    ).findFirst().ifPresent(pickResult -> from.setCountryId(Long.valueOf(pickResult.getValue().toString())));
        travelAfterPickTreeDto.setFrom(from);

        return travelAfterPickTreeDto;
    }
}
