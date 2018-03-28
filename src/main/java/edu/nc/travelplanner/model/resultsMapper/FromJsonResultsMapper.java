package edu.nc.travelplanner.model.resultsMapper;

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
    public TravelDto map(List<PickResult> pickResults) {
        TravelDto travelAfterPickTreeDto = new TravelDto();

        CheckpointDto from = new CheckpointDto();

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

    private void setTravelDtoParam(TravelDto travelDto, PickResult pick, MapNode node){




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
