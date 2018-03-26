package edu.nc.travelplanner.table;


import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "excursion")
public class Excursion {
    @Id
    @Column(name = "excursion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long excursionId;

    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

    private String name;

    private String description;

    private String price;

    private String time;

    @Column(columnDefinition="text")
    private String booking;

    @ManyToMany
    @JoinTable(name = "excursion_travel", joinColumns = {@JoinColumn(name = "excursion_id")}, inverseJoinColumns = {@JoinColumn(name = "travel_id")})
    private List<Travel> travels=new LinkedList<>();

    public Excursion() {
    }

    public Excursion(City city, String name, String description, String price, String time, String booking) {
        this.city = city;
        this.name = name;
        this.description = description;
        this.price = price;
        this.time = time;
        this.booking = booking;
    }

    public long getExcursionId() {
        return excursionId;
    }

    public List<Travel> getTravels() {
        return travels;
    }

    public void setTravels(List<Travel> travels) {
        this.travels = travels;
    }

    public void setExcursionId(long excursionId) {
        this.excursionId = excursionId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }
}

