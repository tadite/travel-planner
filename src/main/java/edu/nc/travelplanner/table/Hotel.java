package edu.nc.travelplanner.table;

import javax.persistence.*;

@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @Column(name = "hote_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    private String name;

    private String address;

    private String price;

    private String pricePeriod;

    private String priceInfo;

    private String booking;

    public Hotel() {
    }

    public Hotel(City city, String name, String address, String price, String pricePeriod, String priceInfo, String booking) {
        this.city = city;
        this.name = name;
        this.address = address;
        this.price = price;
        this.pricePeriod = pricePeriod;
        this.priceInfo = priceInfo;
        this.booking = booking;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPricePeriod() {
        return pricePeriod;
    }

    public void setPricePeriod(String pricePeriod) {
        this.pricePeriod = pricePeriod;
    }

    public String getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(String priceInfo) {
        this.priceInfo = priceInfo;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }
}
