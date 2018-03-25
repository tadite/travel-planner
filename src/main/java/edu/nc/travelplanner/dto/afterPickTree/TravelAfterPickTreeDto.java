package edu.nc.travelplanner.dto.afterPickTree;

import java.util.LinkedList;
import java.util.List;

public class TravelAfterPickTreeDto {
    private Long clientId;
    private CheckpointAfterPickTreeDto fromCheckpoint;
    private CheckpointAfterPickTreeDto toCheckpoint;
    private String name;
    private String dateStart;
    private String dateEnd;
    private String numberOfPersons;
    private String budgetType;

    private HotelAfterPickTreeDto hotel;
    private TwoWayFlightAfterPickTreeDto twoWayFlight;
    private List<ExcursionAfterPickTreeDto> excursions = new LinkedList<>();
    private CarRentAfterPickTreeDto carRent;

    public TravelAfterPickTreeDto() {
    }

    public TravelAfterPickTreeDto(Long clientId, CheckpointAfterPickTreeDto fromCheckpoint, CheckpointAfterPickTreeDto toCheckpoint, String name, String dateStart, String dateEnd, String numberOfPersons, String budgetType, HotelAfterPickTreeDto hotel, TwoWayFlightAfterPickTreeDto twoWayFlight, List<ExcursionAfterPickTreeDto> excursions, CarRentAfterPickTreeDto carRent) {
        this.clientId = clientId;
        this.fromCheckpoint = fromCheckpoint;
        this.toCheckpoint = toCheckpoint;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.numberOfPersons = numberOfPersons;
        this.budgetType = budgetType;
        this.hotel = hotel;
        this.twoWayFlight = twoWayFlight;
        this.excursions = excursions;
        this.carRent = carRent;
    }

    public CheckpointAfterPickTreeDto getFromCheckpoint() {
        return fromCheckpoint;
    }

    public void setFromCheckpoint(CheckpointAfterPickTreeDto fromCheckpoint) {
        this.fromCheckpoint = fromCheckpoint;
    }

    public CheckpointAfterPickTreeDto getToCheckpoint() {
        return toCheckpoint;
    }

    public void setToCheckpoint(CheckpointAfterPickTreeDto toCheckpoint) {
        this.toCheckpoint = toCheckpoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(String numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public HotelAfterPickTreeDto getHotel() {
        return hotel;
    }

    public void setHotel(HotelAfterPickTreeDto hotel) {
        this.hotel = hotel;
    }

    public TwoWayFlightAfterPickTreeDto getTwoWayFlight() {
        return twoWayFlight;
    }

    public void setTwoWayFlight(TwoWayFlightAfterPickTreeDto twoWayFlight) {
        this.twoWayFlight = twoWayFlight;
    }

    public List<ExcursionAfterPickTreeDto> getExcursions() {
        return excursions;
    }

    public void setExcursions(List<ExcursionAfterPickTreeDto> excursions) {
        this.excursions = excursions;
    }

    public CarRentAfterPickTreeDto getCarRent() {
        return carRent;
    }

    public void setCarRent(CarRentAfterPickTreeDto carRent) {
        this.carRent = carRent;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
