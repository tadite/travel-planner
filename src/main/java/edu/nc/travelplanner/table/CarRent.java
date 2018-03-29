package edu.nc.travelplanner.table;

import javax.persistence.*;

@Entity
@Table(name = "car_rent")
public class CarRent {

    @Id
    @Column(name = "car_rent_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carRentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    private String name;

    private String pricePeriod;

    private String price;

    private String seats;

    private String doors;

    private String climate;

    private String transmission;

    private String classType;

    private String mileage;

    @Column(columnDefinition="text")
    private String booking;

    public CarRent() {
    }

    public CarRent(City city, String name, String pricePeriod, String price, String seats, String doors, String climate, String transmission, String classType, String mileage, String booking) {
        this.city = city;
        this.name = name;
        this.pricePeriod = pricePeriod;
        this.price = price;
        this.seats = seats;
        this.doors = doors;
        this.climate = climate;
        this.transmission = transmission;
        this.classType = classType;
        this.mileage = mileage;
        this.booking = booking;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Long getCarRentId() {
        return carRentId;
    }

    public void setCarRentId(Long carRentId) {
        this.carRentId = carRentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPricePeriod() {
        return pricePeriod;
    }

    public void setPricePeriod(String pricePeriod) {
        this.pricePeriod = pricePeriod;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }
}
