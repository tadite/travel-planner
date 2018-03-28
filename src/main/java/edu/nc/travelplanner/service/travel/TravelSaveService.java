package edu.nc.travelplanner.service.travel;

import com.google.common.base.Optional;
import edu.nc.travelplanner.dto.afterPickTree.ExcursionDto;
import edu.nc.travelplanner.dto.afterPickTree.TravelDto;
import edu.nc.travelplanner.mapper.DtoMapper;
import edu.nc.travelplanner.mapper.EntityMapper;
import edu.nc.travelplanner.repository.*;
import edu.nc.travelplanner.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.NotSupportedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TravelSaveService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CarRentRepository carRentRepository;

    @Autowired
    private ExcursionRepository excursionRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private TwoWayFlightRepository twoWayFlightRepository;

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private SimpleDateFormat dateFormat;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private DtoMapper dtoMapper;

    @Transactional
    public TravelDto save(TravelDto travelDto) throws ParseException, NotSupportedException {
        if (travelDto.getToCheckpoint() == null
                || travelDto.getToCheckpoint().getCityName() == null
                || travelDto.getToCheckpoint().getCountryName() == null
                || travelDto.getFromCheckpoint() == null
                || travelDto.getFromCheckpoint().getCountryName() == null
                || travelDto.getFromCheckpoint().getCityName() == null)
            throw new NotSupportedException();

        Travel tempTravel = new Travel();
        tempTravel.setName(travelDto.getName());
        tempTravel.setDateStart(dateFormat.parse(travelDto.getDateStart()));
        tempTravel.setDateEnd(dateFormat.parse(travelDto.getDateEnd()));
        tempTravel.setNumberOfPersons(Integer.valueOf(travelDto.getNumberOfPersons()));
        tempTravel.setBudgetType(travelDto.getBudgetType());

        //save from city
        Country countryFrom = countryRepository.findOptionalByName(travelDto.getFromCheckpoint().getCountryName())
                .or(countryRepository.save(new Country(travelDto.getFromCheckpoint().getCountryName())));

        City cityFrom = cityRepository.findOptionalByName(travelDto.getFromCheckpoint().getCityName())
                .or(cityRepository.save(new City(travelDto.getFromCheckpoint().getCityName(), countryFrom)));
        countryFrom.getCities().add(cityFrom);
        countryFrom = countryRepository.save(countryFrom);

        //save to city
        Country countryTo = countryRepository.findOptionalByName(travelDto.getToCheckpoint().getCountryName())
                .or(countryRepository.save(new Country(travelDto.getToCheckpoint().getCountryName())));

        City cityTo = cityRepository.findOptionalByName(travelDto.getToCheckpoint().getCityName())
                .or(cityRepository.save(new City(travelDto.getToCheckpoint().getCityName(), countryTo)));

        //save hotel
        Hotel hotel = null;
        if (travelDto.getHotel() != null) {
            hotel = hotelRepository.save(new Hotel(cityTo,
                    travelDto.getHotel().getName(),
                    travelDto.getHotel().getAddress(),
                    travelDto.getHotel().getPrice(),
                    travelDto.getHotel().getPricePeriod(),
                    travelDto.getHotel().getPriceInfo(),
                    travelDto.getHotel().getBooking()
            ));
        }

        //save rent
        CarRent carRent = null;
        if (travelDto.getCarRent() != null) {
            carRent = carRentRepository.save(new CarRent(cityTo,
                    travelDto.getCarRent().getName(),
                    travelDto.getCarRent().getPricePeriod(),
                    travelDto.getCarRent().getPrice(),
                    travelDto.getCarRent().getSeats(),
                    travelDto.getCarRent().getDoors(),
                    travelDto.getCarRent().getClimate(),
                    travelDto.getCarRent().getTransmission(),
                    travelDto.getCarRent().getClassType(),
                    travelDto.getCarRent().getMileage(),
                    travelDto.getCarRent().getBooking()
            ));
        }

        //save excursions
        List<Excursion> excursions = new LinkedList<>();
        if (travelDto.getExcursions() != null && travelDto.getExcursions().size() != 0) {
            for (ExcursionDto excursionDto : travelDto.getExcursions()) {
                Excursion excursion = excursionRepository.save(new Excursion(cityTo,
                        excursionDto.getName(),
                        excursionDto.getDescription(),
                        excursionDto.getPrice(),
                        excursionDto.getTime(),
                        excursionDto.getBooking()
                ));
                excursion.setCity(cityTo);
                excursions.add(excursion);
            }
        }

        //save two way flight
        TwoWayFlight twoWayFlight = null;
        if (travelDto.getTwoWayFlight() != null && travelDto.getTwoWayFlight().getFlightTo() != null && travelDto.getTwoWayFlight().getFlightFrom() != null) {

            Flight tempFlightTo = new Flight(travelDto.getTwoWayFlight().getFlightTo().getAircraft(), travelDto.getTwoWayFlight().getFlightTo().getCompanyName(), travelDto.getTwoWayFlight().getFlightTo().getClassType(), travelDto.getTwoWayFlight().getFlightTo().getDepartureDate(), travelDto.getTwoWayFlight().getFlightTo().getDepartureTime(),
                    travelDto.getTwoWayFlight().getFlightTo().getTimeInPath(), travelDto.getTwoWayFlight().getFlightTo().getDepartureCode(), travelDto.getTwoWayFlight().getFlightTo().getDepartureName(), travelDto.getTwoWayFlight().getFlightTo().getArrivalCode(), travelDto.getTwoWayFlight().getFlightTo().getArrivalName());
            tempFlightTo.setToCity(cityTo);
            tempFlightTo.setFromCity(cityFrom);
            Flight flightTo = flightRepository.save(tempFlightTo);

            Flight tempFlightFrom = new Flight(travelDto.getTwoWayFlight().getFlightFrom().getAircraft(), travelDto.getTwoWayFlight().getFlightFrom().getCompanyName(), travelDto.getTwoWayFlight().getFlightFrom().getClassType(), travelDto.getTwoWayFlight().getFlightFrom().getDepartureDate(), travelDto.getTwoWayFlight().getFlightFrom().getDepartureTime(),
                    travelDto.getTwoWayFlight().getFlightFrom().getTimeInPath(), travelDto.getTwoWayFlight().getFlightFrom().getDepartureCode(), travelDto.getTwoWayFlight().getFlightFrom().getDepartureName(), travelDto.getTwoWayFlight().getFlightFrom().getArrivalCode(), travelDto.getTwoWayFlight().getFlightFrom().getArrivalName());
            tempFlightFrom.setToCity(cityFrom);
            tempFlightFrom.setFromCity(cityTo);
            Flight flightFrom = flightRepository.save(tempFlightFrom);

            twoWayFlight = twoWayFlightRepository.save(new TwoWayFlight(flightTo, flightFrom,
                    travelDto.getTwoWayFlight().getPrice(),
                    travelDto.getTwoWayFlight().getBooking()));
        }

        //save travel
        tempTravel.setTwoWayFlight(twoWayFlight);
        //tempTravel.setExcursions(excursions);
        //excursions.forEach(excursion -> tempTravel.getExcursions().add(excursion));
        tempTravel.setCarRent(carRent);
        tempTravel.setHotel(hotel);
        tempTravel.setFromCity(cityFrom);
        tempTravel.setToCity(cityTo);

        Travel travel = travelRepository.save(tempTravel);

        Client client = clientRepository.findOne(travelDto.getClientId());
        client.getTravels().add(travel);
        travel.setClient(client);

        for (Excursion excursion : excursions) {
            excursion.getTravels().add(travel);
            travel.getExcursions().add(excursion);
        }
        excursionRepository.save(excursions);
        travel = travelRepository.save(tempTravel);

        return dtoMapper.map(travel);
    }
}
