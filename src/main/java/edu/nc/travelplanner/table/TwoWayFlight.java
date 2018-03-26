package edu.nc.travelplanner.table;

import javax.persistence.*;

@Entity
@Table(name = "two_way_flight")
public class TwoWayFlight {

    @Id
    @Column(name = "two_wayflight_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long twoWayFlightId;

    @OneToOne
    private Flight flightTo;

    @OneToOne
    private Flight flightFrom;

    private String price;

    private String booking;

    public TwoWayFlight() {
    }

    public TwoWayFlight(Flight flightTo, Flight flightFrom, String price, String booking) {
        this.flightTo = flightTo;
        this.flightFrom = flightFrom;
        this.price = price;
        this.booking = booking;
    }

    public Long getTwoWayFlightId() {
        return twoWayFlightId;
    }

    public void setTwoWayFlightId(Long twoWayFlightId) {
        this.twoWayFlightId = twoWayFlightId;
    }

    public Flight getFlightTo() {
        return flightTo;
    }

    public void setFlightTo(Flight flightTo) {
        this.flightTo = flightTo;
    }

    public Flight getFlightFrom() {
        return flightFrom;
    }

    public void setFlightFrom(Flight flightFrom) {
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
