package edu.nc.travelplanner.model.resultsMapper;

import com.google.common.collect.Iterables;
import com.google.common.reflect.TypeToken;
import edu.nc.travelplanner.config.AuthenticationFacade;
import edu.nc.travelplanner.dao.ClientDao;
import edu.nc.travelplanner.dto.afterPickTree.*;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.action.tableUtil.Column;
import edu.nc.travelplanner.model.action.tableUtil.TablePickResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class SimpleResultsMapper implements ResultsMapper {

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    ClientDao clientDao;

    @Override
    public TravelAfterPickTreeDto map(List<PickResult> pickResults) {
        TravelAfterPickTreeDto travelPickDto = new TravelAfterPickTreeDto();

        setTravelClientId(travelPickDto);
        setCheckpoints(travelPickDto, pickResults);
        setName(travelPickDto, pickResults);
        setDates(travelPickDto, pickResults);
        setHumanData(travelPickDto, pickResults);
        setHotel(travelPickDto, pickResults);
        setTwoWayFlight(travelPickDto, pickResults);
        setExcursions(travelPickDto, pickResults);
        setCarRent(travelPickDto, pickResults);

        return travelPickDto;
    }

    private void setExcursions(TravelAfterPickTreeDto travelDto, List<PickResult> picks) {
        Optional<PickResult> excursionsResultOptional = getSingleResultFromPicks(picks, "checklist-choosingTheExcursion-action-NEW-TABLE");

        List<ExcursionAfterPickTreeDto> excursionDtos = new LinkedList<>();
        excursionsResultOptional.ifPresent(flightPick -> {
            if (!(flightPick.getValue() instanceof LinkedList))
                return;

            //TODO: flightPick.getValue() -> class
            List<?> excursionTables = (List<?>) flightPick.getValue();
            excursionTables.forEach(excursionPick -> {
                if (excursionPick.getClass() != TablePickResult.class)
                    return;

                List<Column> columns = ((TablePickResult) excursionPick).getRow().getColumns();
                excursionDtos.add(createExcursionDto(columns, "title", "description", "price",
                        "time", "experience_id"));
            });
        });
        travelDto.setExcursions(excursionDtos);
    }

    private void setTwoWayFlight(TravelAfterPickTreeDto travelDto, List<PickResult> picks) {
        Optional<PickResult> twoWayFlightResultOptional = getSingleResultFromPicks(picks, "radioButton-typeOfTransport-action-NEW-TABLE");

        twoWayFlightResultOptional.ifPresent(flightPick -> {
            if (flightPick.getResultClass() != TablePickResult.class)
                return;

            List<Column> columns = ((TablePickResult) flightPick.getValue()).getRow().getColumns();

            FlightAfterPickTreeDto toFlightDto = createFlightDto(columns, "flight_to__aircraft", "flight_to__company__name",
                    "flight_to__class", "flight_to__departure_date", "flight_to__departure_time",
                    "flight_to__time_in_path");
            FlightAfterPickTreeDto fromFlightDto = createFlightDto(columns, "flight_from__aircraft", "flight_from__company__name",
                    "flight_from__class", "flight_from__departure_date", "flight_from__departure_time",
                    "flight_from__time_in_path");
            String price = getColumnValueOrEmpty(columns, "price__RUB");
            String booking = getColumnValueOrEmpty(columns, "booking");

            travelDto.setTwoWayFlight(new TwoWayFlightAfterPickTreeDto(toFlightDto, fromFlightDto, price, booking));
        });
    }

    private ExcursionAfterPickTreeDto createExcursionDto(List<Column> columns, String title, String description, String price,
                                                         String time, String booking) {
        String titleVal = getColumnValueOrEmpty(columns, title);
        String descriptionVal = getColumnValueOrEmpty(columns, description);
        String priceVal = getColumnValueOrEmpty(columns, price);
        String timeVal = getColumnValueOrEmpty(columns, time);
        String bookingVal = getColumnValueOrEmpty(columns, booking);

        return new ExcursionAfterPickTreeDto(titleVal, descriptionVal, priceVal, timeVal, bookingVal);
    }

    private FlightAfterPickTreeDto createFlightDto(List<Column> columns, String aircraft, String companyName, String classType,
                                                   String departureDate, String departureTime, String timeInPath) {
        String aircraftVal = getColumnValueOrEmpty(columns, aircraft);
        String companyNameVal = getColumnValueOrEmpty(columns, companyName);
        String classTypeVal = getColumnValueOrEmpty(columns, classType);
        String departureDateVal = getColumnValueOrEmpty(columns, departureDate);
        String departureTimeVal = getColumnValueOrEmpty(columns, departureTime);
        String timeInPathVal = getColumnValueOrEmpty(columns, timeInPath);

        return new FlightAfterPickTreeDto(aircraftVal, companyNameVal, classTypeVal, departureDateVal, departureTimeVal, timeInPathVal);
    }

    private void setHotel(TravelAfterPickTreeDto travelDto, List<PickResult> picks) {
        Optional<PickResult> hotelResultOptional = getSingleResultFromPicks(picks, "radioButton-hotels-find-hotels-action-NEW-TABLE");

        hotelResultOptional.ifPresent(hotelPick -> {
            if (hotelPick.getResultClass() != TablePickResult.class)
                return;

            List<Column> columns = ((TablePickResult) hotelPick.getValue()).getRow().getColumns();
            String name = getColumnValueOrEmpty(columns, "name");
            String address = getColumnValueOrEmpty(columns, "address");
            String price = getColumnValueOrEmpty(columns, "price");
            String pricePeriod = getColumnValueOrEmpty(columns, "price_period");
            String priceInfo = getColumnValueOrEmpty(columns, "price_info");
            String booking = getColumnValueOrEmpty(columns, "booking");

            travelDto.setHotel(new HotelAfterPickTreeDto(name, address, price, pricePeriod, priceInfo, booking));
        });
    }

    private void setHumanData(TravelAfterPickTreeDto travelDto, List<PickResult> picks) {
        getSingleResultFromPicks(picks, "textinput-numberOfPersons-action")
                .ifPresent(pick -> travelDto.setNumberOfPersons(String.valueOf(pick.getValue())));
        getSingleResultFromPicks(picks, "dropdownlist-budget-action.Value")
                .ifPresent(pick -> travelDto.setBudgetType(String.valueOf(pick.getValue())));

    }

    private void setDates(TravelAfterPickTreeDto travelDto, List<PickResult> picks) {
        getSingleResultFromPicks(picks, "dateIntervalInput-travelPeriod-action1")
                .ifPresent(pick -> travelDto.setDateStart(String.valueOf(pick.getValue())));
        getSingleResultFromPicks(picks, "dateIntervalInput-travelPeriod-action2")
                .ifPresent(pick -> travelDto.setDateEnd(String.valueOf(pick.getValue())));

    }

    private void setName(TravelAfterPickTreeDto travelDto, List<PickResult> picks) {
        getSingleResultFromPicks(picks, "textinput-nameOfTravel-action")
                .ifPresent(pick -> travelDto.setName(String.valueOf(pick.getValue())));
    }

    private void setCarRent(TravelAfterPickTreeDto travelDto, List<PickResult> picks) {
        Optional<PickResult> carRentResultOptional = getSingleResultFromPicks(picks, "radioButton-rentCar-action-NEW-TABLE");

        carRentResultOptional.ifPresent(carRentPick -> {
            if (carRentPick.getResultClass() != TablePickResult.class)
                return;

            List<Column> columns = ((TablePickResult) carRentPick.getValue()).getRow().getColumns();
            String name = getColumnValueOrEmpty(columns, "name");
            String pricePeriod = getColumnValueOrEmpty(columns, "price-period");
            String price = getColumnValueOrEmpty(columns, "price");
            String seats = getColumnValueOrEmpty(columns, "seats");
            String doors = getColumnValueOrEmpty(columns, "doors");
            String climate = getColumnValueOrEmpty(columns, "climate");
            String transmission = getColumnValueOrEmpty(columns, "transmission");
            String classType = getColumnValueOrEmpty(columns, "class");
            String mileage = getColumnValueOrEmpty(columns, "mileage");
            String booking = getColumnValueOrEmpty(columns, "booking");

            travelDto.setCarRent(new CarRentAfterPickTreeDto(name, pricePeriod, price, seats, doors, climate,
                    transmission, classType, mileage, booking));
        });
    }

    private void setCheckpoints(TravelAfterPickTreeDto travelDto, List<PickResult> picks) {
        travelDto.setFromCheckpoint(createCheckpointDto(picks,
                "dropdownlist-departure-country-action.Value",
                "dropdownlist-departure-city-action.Value"));

        travelDto.setToCheckpoint(createCheckpointDto(picks,
                "dropdownlist-countries-action.Value",
                "dropdownlist-citiesByCountryId-action.Value"));
    }

    private void setTravelClientId(TravelAfterPickTreeDto travelPickDto) {
        travelPickDto.setClientId(clientDao.getClientByLogin(authenticationFacade.getAuthentication().getName()).getClientId());
    }

    private Optional<PickResult> getSingleResultFromPicks(List<PickResult> picks, String key) {
        return picks.stream()
                .filter(pick -> pick.getKey().equals(key))
                .findFirst();
    }

    private CheckpointAfterPickTreeDto createCheckpointDto(List<PickResult> picks, String keyCountry, String keyCity) {
        String countryName = getSingleStringFromPicksOrEmpty(picks, keyCountry);
        String cityName = getSingleStringFromPicksOrEmpty(picks, keyCity);

        return new CheckpointAfterPickTreeDto(countryName, cityName);
    }

    private String getSingleStringFromPicksOrEmpty(List<PickResult> picks, String key) {
        return getSingleResultFromPicks(picks, key)
                .map(pick -> String.valueOf(pick.getValue()))
                .orElse("");
    }

    private String getColumnValueOrEmpty(List<Column> columns, String name) {
        return Iterables.tryFind(columns, col -> col.getName().equals(name))
                .transform(Column::getValue)
                .or("");
    }
}
