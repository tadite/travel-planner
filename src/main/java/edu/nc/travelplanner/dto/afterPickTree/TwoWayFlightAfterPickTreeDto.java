package edu.nc.travelplanner.dto.afterPickTree;

public class TwoWayFlightAfterPickTreeDto {
    private FlightAfterPickTreeDto flightTo;
    private FlightAfterPickTreeDto flightFrom;
    private String price;
    private String booking;

    public TwoWayFlightAfterPickTreeDto(FlightAfterPickTreeDto flightTo, FlightAfterPickTreeDto flightFrom, String price, String booking) {
        this.flightTo = flightTo;
        this.flightFrom = flightFrom;
        this.price = price;
        this.booking = booking;
    }

    public FlightAfterPickTreeDto getFlightTo() {
        return flightTo;
    }

    public void setFlightTo(FlightAfterPickTreeDto flightTo) {
        this.flightTo = flightTo;
    }

    public FlightAfterPickTreeDto getFlightFrom() {
        return flightFrom;
    }

    public void setFlightFrom(FlightAfterPickTreeDto flightFrom) {
        this.flightFrom = flightFrom;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }
}
