package edu.nc.travelplanner;

import edu.nc.travelplanner.dto.afterPickTree.*;
import edu.nc.travelplanner.service.travel.TravelSaveService;
import edu.nc.travelplanner.table.CarRent;
import edu.nc.travelplanner.table.TwoWayFlight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.NotSupportedException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TravelplannerApplication.class)
@WebAppConfiguration
public class SpringDataTests {

    @Autowired
    TravelSaveService travelSaveService;

    @Test
    public void canAddTravel() throws ParseException, NotSupportedException {
        CheckpointDto checkpointDtoFrom = new CheckpointDto("Russia", "Voronezh");
        CheckpointDto checkpointDtoTo = new CheckpointDto("Russia", "Moscow");

        HotelDto hotelDto = new HotelDto("hotel-test-name","hotel-test-address","hotel-test-price","hotel-test-pricePeriod","hotel-test-priceInfo","booking");

        FlightDto flightDtoFrom = new FlightDto("flight-from-aircraft", "flight-from-companyName", "flight-from-classType", "flight-from-dapDate",
                "flight-from-depTime","flight-from-timeInPath","flight-from-depCode","flight-from-depName",
                "flight-from-arrivalCode","flight-from-arrivalName");
        FlightDto flightDtoTo = new FlightDto("flight-to-aircraft", "flight-to-companyName", "flight-to-classType", "flight-to-dapDate",
                "flight-to-depTime","flight-to-timeInPath","flight-to-depCode","flight-to-depName",
                "flight-to-arrivalCode","flight-to-arrivalName");
        TwoWayFlightDto twoWayFlightDto = new TwoWayFlightDto(flightDtoTo, flightDtoFrom, "twoWayFlight-price", "twoWayFlight-booking");

        List<ExcursionDto> excursionDtos = new LinkedList<>();
        excursionDtos.add(new ExcursionDto("excursion-1-name", "excursion-1-desc", "excursion-1-price", "excursion-1-time","excursion-1-booking"));
        excursionDtos.add(new ExcursionDto("excursion-2-name", "excursion-2-desc", "excursion-2-price", "excursion-2-time","excursion-2-booking"));

        CarRentDto carRentDto = new CarRentDto("carRent-name","carRent-pricePeriod","carRent-price","carRent-seats",
                "carRent-doors","carRent-climate","carRent-transmission","carRent-classType","carRent-mileage"
                ,"carRent-booking"
        );

        TravelDto travelDto = new TravelDto(1L,checkpointDtoFrom,checkpointDtoTo,"test-travel","2018-03-26","2018-03-30",
                "2", "Ultra Premium", hotelDto, twoWayFlightDto, excursionDtos, carRentDto);

        TravelDto savedTravelDto = travelSaveService.save(travelDto);

        savedTravelDto.getCarRent();
    }

    @Test
    public void canUpdateProfile(){

    }
}
