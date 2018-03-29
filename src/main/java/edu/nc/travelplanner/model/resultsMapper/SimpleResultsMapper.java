package edu.nc.travelplanner.model.resultsMapper;

import com.google.common.collect.Iterables;
import edu.nc.travelplanner.config.AuthenticationFacade;
import edu.nc.travelplanner.dao.ClientDao;
import edu.nc.travelplanner.dto.afterPickTree.*;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.action.tableUtil.Column;
import edu.nc.travelplanner.model.action.tableUtil.TablePickResult;
import edu.nc.travelplanner.table.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SimpleResultsMapper implements ResultsMapper {

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    ClientDao clientDao;

    @Override
    public TravelDto map(List<PickResult> pickResults) {
        TravelDto travelPickDto = new TravelDto();

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

    private void setExcursions(TravelDto travelDto, List<PickResult> picks) {
        Optional<PickResult> excursionsResultOptional = getSingleResultFromPicks(picks, "checklist-choosingTheExcursion-action-NEW-TABLE");

        List<ExcursionDto> excursionDtos = new LinkedList<>();
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

    private void setTwoWayFlight(TravelDto travelDto, List<PickResult> picks) {
        Optional<PickResult> twoWayFlightResultOptional = getSingleResultFromPicks(picks, "radioButton-typeOfTransport-action-NEW-TABLE");

        twoWayFlightResultOptional.ifPresent(flightPick -> {
            if (flightPick.getResultClass() != TablePickResult.class)
                return;

            List<Column> columns = ((TablePickResult) flightPick.getValue()).getRow().getColumns();

            FlightDto toFlightDto = createFlightDto(columns, "flight_to__aircraft", "flight_to__company__name",
                    "flight_to__class", "flight_to__departure_date", "flight_to__departure_time",
                    "flight_to__time_in_path", "flight_to__departure__place_code", "flight_to__departure__place_name",
                    "flight_to__arrival__place_code", "flight_to__arrival__place_name", "flight_from__transfer");

            FlightDto fromFlightDto = createFlightDto(columns, "flight_from__aircraft", "flight_from__company__name",
                    "flight_from__class", "flight_from__departure_date", "flight_from__departure_time",
                    "flight_from__time_in_path", "flight_from__departure__place_code", "flight_from__departure__place_name",
                    "flight_from__arrival__place_code", "flight_from__arrival__place_name", "flight_from__transfer");

            String price = getColumnStringValueOrEmpty(columns, "price__RUB");
            String booking = getColumnStringValueOrEmpty(columns, "booking");

            travelDto.setTwoWayFlight(new TwoWayFlightDto(toFlightDto, fromFlightDto, price, booking));
        });
    }

    private ExcursionDto createExcursionDto(List<Column> columns, String title, String description, String price,
                                            String time, String booking) {
        String titleVal = getColumnStringValueOrEmpty(columns, title);
        String descriptionVal = getColumnStringValueOrEmpty(columns, description);
        String priceVal = getColumnStringValueOrEmpty(columns, price);
        String timeVal = getColumnStringValueOrEmpty(columns, time);
        String bookingVal = getColumnStringValueOrEmpty(columns, booking);

        return new ExcursionDto(titleVal, descriptionVal, priceVal, timeVal, bookingVal);
    }

    private FlightDto createFlightDto(List<Column> columns, String aircraft, String companyName, String classType,
                                      String departureDate, String departureTime, String timeInPath, String departureCode,
                                      String departureName, String arrivalCode, String arrivalName, String transferName) {
        String aircraftVal = getColumnStringValueOrEmpty(columns, aircraft);
        String companyNameVal = getColumnStringValueOrEmpty(columns, companyName);
        String classTypeVal = getColumnStringValueOrEmpty(columns, classType);
        String departureDateVal = getColumnStringValueOrEmpty(columns, departureDate);
        String departureTimeVal = getColumnStringValueOrEmpty(columns, departureTime);
        String timeInPathVal = getColumnStringValueOrEmpty(columns, timeInPath);
        String departureCodeVal = getColumnStringValueOrEmpty(columns, departureCode);
        String departureNameVal = getColumnStringValueOrEmpty(columns, departureName);
        String arrivalCodeVal = getColumnStringValueOrEmpty(columns, arrivalCode);
        String arrivalNameVal = getColumnStringValueOrEmpty(columns, arrivalName);

        List<FlightTransferDto> transferDtos = new LinkedList<>();
        List<Column> tempTransferColumns = getColumnsStartWithNameObjectValueOrEmpty(columns, transferName);
        if (tempTransferColumns!=null && tempTransferColumns.size()>0){

            for (Column transferColumn : tempTransferColumns) {
                String transferColName = transferColumn.getName();
                List<Column> transferColumnsValue = (List<Column>)transferColumn.getValue();
                String transferPlaceCode = getColumnStringValueOrEmpty(transferColumnsValue, "place_code");
                String transferPlaceName = getColumnStringValueOrEmpty(transferColumnsValue, "place_name");
                String transferTime = getColumnStringValueOrEmpty(transferColumnsValue, "transfer_time");
                String transferArrivalDate = getColumnStringValueOrEmpty(transferColumnsValue, "arrival_date");
                String transferDepartureDate = getColumnStringValueOrEmpty(transferColumnsValue, "departure_date");
                transferDtos.add(new FlightTransferDto(transferPlaceCode, transferPlaceName, transferTime, transferArrivalDate, transferDepartureDate));
            }
        }

        return new FlightDto(aircraftVal, companyNameVal, classTypeVal, departureDateVal,
                departureTimeVal, timeInPathVal, departureCodeVal, departureNameVal,
                arrivalCodeVal, arrivalNameVal, transferDtos);
    }

    private void setHotel(TravelDto travelDto, List<PickResult> picks) {
        Optional<PickResult> hotelResultOptional = getSingleResultFromPicks(picks, "radioButton-hotels-find-hotels-action-NEW-TABLE");

        hotelResultOptional.ifPresent(hotelPick -> {
            if (hotelPick.getResultClass() != TablePickResult.class)
                return;

            List<Column> columns = ((TablePickResult) hotelPick.getValue()).getRow().getColumns();
            String name = getColumnStringValueOrEmpty(columns, "name");
            String address = getColumnStringValueOrEmpty(columns, "address");
            String price = getColumnStringValueOrEmpty(columns, "price");
            String pricePeriod = getColumnStringValueOrEmpty(columns, "price_period");
            String priceInfo = getColumnStringValueOrEmpty(columns, "price_info");
            String booking = getColumnStringValueOrEmpty(columns, "booking");

            travelDto.setHotel(new HotelDto(name, address, price, pricePeriod, priceInfo, booking));
        });
    }

    private void setHumanData(TravelDto travelDto, List<PickResult> picks) {
        getSingleResultFromPicks(picks, "textinput-numberOfPersons-action")
                .ifPresent(pick -> travelDto.setNumberOfPersons(String.valueOf(pick.getValue())));
        getSingleResultFromPicks(picks, "dropdownlist-budget-action.Value")
                .ifPresent(pick -> travelDto.setBudgetType(String.valueOf(pick.getValue())));

    }

    private void setDates(TravelDto travelDto, List<PickResult> picks) {
        getSingleResultFromPicks(picks, "dateIntervalInput-travelPeriod-action1")
                .ifPresent(pick -> travelDto.setDateStart(String.valueOf(pick.getValue())));
        getSingleResultFromPicks(picks, "dateIntervalInput-travelPeriod-action2")
                .ifPresent(pick -> travelDto.setDateEnd(String.valueOf(pick.getValue())));

    }

    private void setName(TravelDto travelDto, List<PickResult> picks) {
        getSingleResultFromPicks(picks, "textinput-nameOfTravel-action")
                .ifPresent(pick -> travelDto.setName(String.valueOf(pick.getValue())));
    }

    private void setCarRent(TravelDto travelDto, List<PickResult> picks) {
        Optional<PickResult> carRentResultOptional = getSingleResultFromPicks(picks, "radioButton-rentCar-action-NEW-TABLE");

        carRentResultOptional.ifPresent(carRentPick -> {
            if (carRentPick.getResultClass() != TablePickResult.class)
                return;

            List<Column> columns = ((TablePickResult) carRentPick.getValue()).getRow().getColumns();
            String name = getColumnStringValueOrEmpty(columns, "name");
            String pricePeriod = getColumnStringValueOrEmpty(columns, "price-period");
            String price = getColumnStringValueOrEmpty(columns, "price");
            String seats = getColumnStringValueOrEmpty(columns, "seats");
            String doors = getColumnStringValueOrEmpty(columns, "doors");
            String climate = getColumnStringValueOrEmpty(columns, "climate");
            String transmission = getColumnStringValueOrEmpty(columns, "transmission");
            String classType = getColumnStringValueOrEmpty(columns, "class");
            String mileage = getColumnStringValueOrEmpty(columns, "mileage");
            String booking = getColumnStringValueOrEmpty(columns, "booking");

            travelDto.setCarRent(new CarRentDto(name, pricePeriod, price, seats, doors, climate,
                    transmission, classType, mileage, booking));
        });
    }

    private void setCheckpoints(TravelDto travelDto, List<PickResult> picks) {
        travelDto.setFromCheckpoint(createCheckpointDto(picks,
                "dropdownlist-departure-country-action.Value",
                "dropdownlist-departure-city-action.Value"));

        travelDto.setToCheckpoint(createCheckpointDto(picks,
                "dropdownlist-countries-action.Value",
                "dropdownlist-citiesByCountryId-action.Value"));
    }

    private void setTravelClientId(TravelDto travelPickDto) {
        Client clientByLogin = clientDao.getClientByLogin(authenticationFacade.getAuthentication().getName());
        travelPickDto.setClientId(clientByLogin.getClientId());
        travelPickDto.setClientUsername(clientByLogin.getLogin());
    }

    private Optional<PickResult> getSingleResultFromPicks(List<PickResult> picks, String key) {
        return picks.stream()
                .filter(pick -> pick.getKey().equals(key))
                .findFirst();
    }

    private CheckpointDto createCheckpointDto(List<PickResult> picks, String keyCountry, String keyCity) {
        String countryName = getSingleStringFromPicksOrEmpty(picks, keyCountry);
        String cityName = getSingleStringFromPicksOrEmpty(picks, keyCity);

        return new CheckpointDto(countryName, cityName);
    }

    private String getSingleStringFromPicksOrEmpty(List<PickResult> picks, String key) {
        return getSingleResultFromPicks(picks, key)
                .map(pick -> String.valueOf(pick.getValue()))
                .orElse("");
    }

    private String getColumnStringValueOrEmpty(List<Column> columns, String name) {
        return Iterables.tryFind(columns, col -> col.getName().equals(name))
                .transform(col -> String.valueOf(col.getValue()))
                .or("");
    }

    private List<Column> getColumnsStartWithNameObjectValueOrEmpty(List<Column> columns, String name) {
        return columns.stream().filter(col -> col.getName().startsWith(name)).collect(Collectors.toList());
    }
}
