package edu.nc.travelplanner.dto.afterPickTree;

import java.util.LinkedList;
import java.util.List;

public class TravelDto {
    private Long travelId;
    private Long clientId;
    private String clientUsername;
    private CheckpointDto fromCheckpoint;
    private CheckpointDto toCheckpoint;
    private String name;
    private String dateStart;
    private String dateEnd;
    private String numberOfPersons;
    private String budgetType;

    private HotelDto hotel;
    private TwoWayFlightDto twoWayFlight;
    private List<ExcursionDto> excursions = new LinkedList<>();
    private CarRentDto carRent;

    public TravelDto() {
    }

    public TravelDto(Long clientId, CheckpointDto fromCheckpoint, CheckpointDto toCheckpoint, String name, String dateStart, String dateEnd, String numberOfPersons, String budgetType, HotelDto hotel, TwoWayFlightDto twoWayFlight, List<ExcursionDto> excursions, CarRentDto carRent) {
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

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public CheckpointDto getFromCheckpoint() {
        return fromCheckpoint;
    }

    public void setFromCheckpoint(CheckpointDto fromCheckpoint) {
        this.fromCheckpoint = fromCheckpoint;
    }

    public CheckpointDto getToCheckpoint() {
        return toCheckpoint;
    }

    public void setToCheckpoint(CheckpointDto toCheckpoint) {
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

    public HotelDto getHotel() {
        return hotel;
    }

    public void setHotel(HotelDto hotel) {
        this.hotel = hotel;
    }

    public TwoWayFlightDto getTwoWayFlight() {
        return twoWayFlight;
    }

    public void setTwoWayFlight(TwoWayFlightDto twoWayFlight) {
        this.twoWayFlight = twoWayFlight;
    }

    public List<ExcursionDto> getExcursions() {
        return excursions;
    }

    public void setExcursions(List<ExcursionDto> excursions) {
        this.excursions = excursions;
    }

    public CarRentDto getCarRent() {
        return carRent;
    }

    public void setCarRent(CarRentDto carRent) {
        this.carRent = carRent;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
