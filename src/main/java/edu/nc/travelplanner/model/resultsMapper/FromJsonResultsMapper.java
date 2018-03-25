package edu.nc.travelplanner.model.resultsMapper;

import edu.nc.travelplanner.dto.afterPickTree.CheckpointAfterPickTreeDto;
import edu.nc.travelplanner.dto.afterPickTree.TravelAfterPickTreeDto;
import edu.nc.travelplanner.model.action.DateInterval;
import edu.nc.travelplanner.model.action.PickResult;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FromJsonResultsMapper /*implements ResultsMapper */{
/*
    private List<MapNode> mapNodes;
    private final List<String> travelParams =new LinkedList<String>(){{
        push("from.cityId");
        push("from.countryId");
        push("to.cityId");
        push("to.countryId");
        push("travelPeriodStart");
        push("travelPeriodEnd");

        push("excursion.id");
        push("excursion.typeId");
        push("excursion.name");
        push("excursion.booking_link");

        push("budget.id");
        push("travelName");
        push("numberOfPersons");
        push("rentCarId");
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




    }

    private Object getPickValue(PickResult pick, MapNode node){
        switch (node.getType()){
            case "list-first":
                return ((List<String>)pick.getValue()).get(0);
            default:
                return pick.getValue();
        }
    }*/
}
