package edu.nc.travelplanner.model.resultsMapper;

import edu.nc.travelplanner.dto.afterPickTree.CheckpointAfterPickTreeDto;
import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.model.action.PickResult;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FromJsonResultsMapper implements ResultsMapper {

    private List<MapNode> mapNodes;
    private final List<String> travelParams =new LinkedList<String>(){{
        push("from.cityId");
        push("from.countryId");
    }};

    public FromJsonResultsMapper(List<MapNode> mapNodes) {
        this.mapNodes = mapNodes;
    }

    @Override
    public TravelAfterPickTreeDto map(List<PickResult> pickResults) {
        TravelAfterPickTreeDto travelAfterPickTreeDto = new TravelAfterPickTreeDto();

        CheckpointAfterPickTreeDto from = new CheckpointAfterPickTreeDto();

        for (MapNode node : mapNodes) {
            if (!travelParams.contains(node.getTo()))
                continue;

            Optional<PickResult> pickOptional = pickResults.stream()
                    .filter(pick -> pick.getKey().equals(node.getFrom()))
                    .findFirst();

            if (pickOptional.isPresent()){
                PickResult pick = pickOptional.get();
                setTravelDtoParam(travelAfterPickTreeDto, pick, node);
            }
        }

        return travelAfterPickTreeDto;
    }

    private void setTravelDtoParam(TravelAfterPickTreeDto travelDto, PickResult pick, MapNode node){
        if (node.getTo().equals("from.cityId"))
                travelDto.getFrom().setCityId(Long.valueOf((String)getPickValue(pick,node)));
        else if (node.getTo().equals("from.countryId"))
            travelDto.getFrom().setCountryId(Long.valueOf((String)getPickValue(pick,node)));

    }

    private Object getPickValue(PickResult pick, MapNode node){
        switch (node.getType()){
            case "list-first":
                return ((List<String>)pick.getValue()).get(0);
            default:
                return pick.getValue();
        }
    }
}
