package edu.nc.travelplanner.dto.afterPickTree;

public class FlightTransferDto {
    private Long flightTransferId;
    private String placeCode;
    private String placeName;
    private String transferTime;
    private String arrivalDate;
    private String departureDate;

    public FlightTransferDto() {
    }

    public FlightTransferDto(String placeCode, String placeName, String transferTime, String arrivalDate, String departureDate) {
        this.placeCode = placeCode;
        this.placeName = placeName;
        this.transferTime = transferTime;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    public Long getFlightTransferId() {
        return flightTransferId;
    }

    public void setFlightTransferId(Long flightTransferId) {
        this.flightTransferId = flightTransferId;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }
}
