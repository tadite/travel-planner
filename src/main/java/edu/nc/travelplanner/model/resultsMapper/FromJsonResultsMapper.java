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
        push("from.optionId");
        push("from.typeOfTravelId");
        push("from.viewOfTravel");
        push("from.travelPeriod");
        push("from.budget");
        push("from.departureCityId");
        push("from.departureCountryId");
        push("from.kindOfActivity");
        push("from.typeOfExcursion");
        push("from.typeOfMovement");
        push("from.numberOfPersons");
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
        if (node.getTo().equals("from.optionId"))
            travelDto.getFrom().setOptionId(Integer.valueOf((String)getPickValue(pick,node)));
        if (node.getTo().equals("from.typeOfTravelId"))
            travelDto.getFrom().setTypeOfTravelId(Integer.valueOf((String)getPickValue(pick,node)));
        if (node.getTo().equals("from.viewOfTravel"))
            travelDto.getFrom().setViewOfTravel((String)getPickValue(pick,node));
        if (node.getTo().equals("from.travelPeriod"))
            travelDto.getFrom().setTravelPeriod((String)getPickValue(pick,node));
        if (node.getTo().equals("from.budget"))
            travelDto.getFrom().setBudget((String)getPickValue(pick,node));
        if (node.getTo().equals("from.departureCityId"))
            travelDto.getFrom().setDepartureCityId(Long.valueOf((String)getPickValue(pick,node)));
        if (node.getTo().equals("from.departureCountryId"))
            travelDto.getFrom().setDepartureCountryId(Integer.valueOf((String)getPickValue(pick,node)));
        if (node.getTo().equals("from.kindOfActivity"))
            travelDto.getFrom().setKindOfActivity((String)getPickValue(pick,node));
        if (node.getTo().equals("from.typeOfExcursion"))
            travelDto.getFrom().setTypeOfExcursion((String)getPickValue(pick,node));
        if (node.getTo().equals("from.typeOfMovement"))
            travelDto.getFrom().setTypeOfMovement((String)getPickValue(pick,node));
        if (node.getTo().equals("from.numberOfPersons"))
            travelDto.getFrom().setNumberOfPerson(Integer.valueOf((String)getPickValue(pick,node)));
        else if (node.getTo().equals("from.countryId"))
            travelDto.getFrom().setCountryId(Integer.valueOf((String)getPickValue(pick,node)));

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
