package edu.nc.travelplanner.dto.afterPickTree;

public class TwoWayFlightDto {
    private FlightDto flightTo;
    private FlightDto flightFrom;
    private String price;
    private String booking;

    public TwoWayFlightDto() {
    }

    public TwoWayFlightDto(FlightDto flightTo, FlightDto flightFrom, String price, String booking) {
        this.flightTo = flightTo;
        this.flightFrom = flightFrom;
        this.price = price;
        this.booking = booking;
    }

    public FlightDto getFlightTo() {
        return flightTo;
    }

    public void setFlightTo(FlightDto flightTo) {
        this.flightTo = flightTo;
    }

    public FlightDto getFlightFrom() {
        return flightFrom;
    }

    public void setFlightFrom(FlightDto flightFrom) {
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
