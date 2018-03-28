package edu.nc.travelplanner.mapper;

import edu.nc.travelplanner.dto.afterPickTree.FlightDto;
import edu.nc.travelplanner.table.Flight;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public Flight map(FlightDto flightDto){
        return new Flight(flightDto.getAircraft(),flightDto.getCompanyName(),flightDto.getClassType(),flightDto.getDepartureDate(),flightDto.getDepartureTime(),
                flightDto.getTimeInPath(), flightDto.getDepartureCode(), flightDto.getDepartureName(),flightDto.getArrivalCode(),flightDto.getArrivalName());
    }
}
