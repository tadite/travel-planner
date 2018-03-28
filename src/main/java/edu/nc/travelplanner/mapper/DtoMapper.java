package edu.nc.travelplanner.mapper;

import edu.nc.travelplanner.dto.afterPickTree.*;
import edu.nc.travelplanner.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    @Autowired
    SimpleDateFormat simpleDateFormat;

    public CheckpointDto map(City city) {
        CheckpointDto dto = new CheckpointDto();
        dto.setCityName(city.getName());
        dto.setCountryName(city.getCountry().getName());
        return dto;
    }

    public HotelDto map(Hotel hotel) {
        HotelDto dto = new HotelDto();
        dto.setHotelId(hotel.getHotelId());
        dto.setAddress(hotel.getAddress());
        dto.setBooking(hotel.getBooking());
        dto.setName(hotel.getName());
        dto.setPrice(hotel.getPrice());
        dto.setPriceInfo(hotel.getPriceInfo());
        dto.setPricePeriod(hotel.getPricePeriod());
        return dto;
    }

    public ExcursionDto map(Excursion excursion) {
        ExcursionDto dto = new ExcursionDto();
        dto.setExcursionId(excursion.getExcursionId());
        dto.setBooking(excursion.getBooking());
        dto.setDescription(excursion.getDescription());
        dto.setName(excursion.getName());
        dto.setPrice(excursion.getPrice());
        dto.setTime(excursion.getTime());
        return dto;
    }

    public CarRentDto map(CarRent carRent) {
        CarRentDto dto = new CarRentDto();
        dto.setCarRentId(carRent.getCarRentId());
        dto.setBooking(carRent.getBooking());
        dto.setClimate(carRent.getClimate());
        dto.setDoors(carRent.getDoors());
        dto.setMileage(carRent.getMileage());
        dto.setName(carRent.getName());
        dto.setPrice(carRent.getPrice());
        dto.setPricePeriod(carRent.getPricePeriod());
        dto.setClassType(carRent.getClassType());
        dto.setSeats(carRent.getSeats());
        dto.setTransmission(carRent.getTransmission());
        return dto;
    }

    public FlightDto map(Flight flight) {
        FlightDto dto = new FlightDto();
        dto.setFlightId(flight.getFlightId());
        dto.setAircraft(flight.getAircraft());
        dto.setArrivalCode(flight.getArrivalCode());
        dto.setArrivalName(flight.getArrivalName());
        dto.setClassType(flight.getClassType());
        dto.setCompanyName(flight.getCompanyName());
        dto.setDepartureCode(flight.getDepartureCode());
        dto.setDepartureDate(flight.getDepartureDate());
        dto.setDepartureName(flight.getDepartureName());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setTimeInPath(flight.getTimeInPath());
        return dto;
    }

    public TwoWayFlightDto map(TwoWayFlight twoWayFlight) {
        TwoWayFlightDto dto = new TwoWayFlightDto();
        dto.setBooking(twoWayFlight.getBooking());
        dto.setPrice(twoWayFlight.getPrice());
        dto.setFlightFrom(map(twoWayFlight.getFlightFrom()));
        dto.setFlightTo(map(twoWayFlight.getFlightTo()));
        return dto;
    }

    public String mapDate(Date date) {
        return simpleDateFormat.format(date);
    }

    public TravelDto map(Travel travel) {
        TravelDto dto = new TravelDto();
        dto.setTravelId(travel.getTravelId());
        if (travel.getClient() != null) {
            dto.setClientId(travel.getClient().getClientId());
            dto.setClientUsername(travel.getClient().getUsername());
        }
        if (travel.getFromCity() != null)
            dto.setFromCheckpoint(map(travel.getFromCity()));
        if (travel.getToCity() != null)
            dto.setToCheckpoint(map(travel.getToCity()));
        dto.setName(travel.getName());
        if (travel.getDateStart() != null)
            dto.setDateStart(mapDate(travel.getDateStart()));
        if (travel.getDateEnd() != null)
            dto.setDateEnd(mapDate(travel.getDateEnd()));
        if (travel.getNumberOfPersons() != null)
            dto.setNumberOfPersons(travel.getNumberOfPersons().toString());
        dto.setBudgetType(travel.getBudgetType());
        if (travel.getHotel() != null)
            dto.setHotel(map(travel.getHotel()));
        if (travel.getTwoWayFlight() != null)
            dto.setTwoWayFlight(map(travel.getTwoWayFlight()));
        if (travel.getExcursions() != null)
            dto.setExcursions(travel.getExcursions().stream().map(this::map).collect(Collectors.toList()));
        if (travel.getCarRent() != null)
            dto.setCarRent(map(travel.getCarRent()));
        return dto;
    }
}
