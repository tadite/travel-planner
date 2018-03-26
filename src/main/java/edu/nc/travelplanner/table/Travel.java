package edu.nc.travelplanner.table;

import edu.nc.travelplanner.dto.afterPickTree.CheckpointDto;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "travel")
public class Travel {
    @Id
    @Column(name = "travel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long travelId;

    @ManyToOne
    private Client client;

    @ManyToOne
    private City fromCity;

    @ManyToOne
    private City toCity;

    private String name;

    private Date dateStart;

    private Date dateEnd;

    private Integer numberOfPersons;

    private String budgetType;

    @ManyToOne
    private CarRent carRent;

    @ManyToOne
    private Hotel hotel;

    @ManyToOne
    private TwoWayFlight twoWayFlight;

    @ManyToMany(mappedBy = "travels")
    private List<Excursion> excursions = new LinkedList<>();

    public long getTravelId() {
        return travelId;
    }

    public void setTravelId(long travelId) {
        this.travelId = travelId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public City getFromCity() {
        return fromCity;
    }

    public void setFromCity(City fromCity) {
        this.fromCity = fromCity;
    }

    public City getToCity() {
        return toCity;
    }

    public void setToCity(City toCity) {
        this.toCity = toCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(Integer numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public CarRent getCarRent() {
        return carRent;
    }

    public void setCarRent(CarRent carRent) {
        this.carRent = carRent;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public TwoWayFlight getTwoWayFlight() {
        return twoWayFlight;
    }

    public void setTwoWayFlight(TwoWayFlight twoWayFlight) {
        this.twoWayFlight = twoWayFlight;
    }

    public List<Excursion> getExcursions() {
        return excursions;
    }

    public void setExcursions(List<Excursion> excursions) {
        this.excursions = excursions;
    }
}
