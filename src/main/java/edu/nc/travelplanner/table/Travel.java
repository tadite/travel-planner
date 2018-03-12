package edu.nc.travelplanner.table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "travel")
public class Travel {
    @Id
    @Column(name = "travel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long travelId;

    @Column(name = "name")
    private String name;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private String cost;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "travel")
    private Set<TravelForClient> travelForClients = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "travel")
    private List<CheckPoint> checkPoints = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "travel")
    private Set<OptionForTravel> optionForTravels = new HashSet<>();

    public CheckPoint getFromCheckPoint(){
        if (checkPoints.isEmpty())
            return null;
        return checkPoints.get(0);
    }

    public CheckPoint getToCheckPoint(){
        if (checkPoints.isEmpty())
            return null;
        return checkPoints.get(checkPoints.size() - 1);
    }

    public long getTravelId() {
        return travelId;
    }

    public void setTravelId(long travelId) {
        this.travelId  = travelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays  = numberOfDays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description  = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost  = cost;
    }

    public void setTravelForClients(Set<TravelForClient> travelForClients) {
        this.travelForClients = travelForClients;
    }

    public void setCheckPoints(List<CheckPoint> checkPoints) {
        this.checkPoints = checkPoints;
    }

    public void setOptionForTravels(Set<OptionForTravel> optionForTravels) {
        this.optionForTravels = optionForTravels;
    }

    public void addCheckPoint(CheckPoint checkPoint) {
        this.checkPoints.add(checkPoint);
    }

    public void removeCheckPoint(CheckPoint checkPoint) {
        checkPoints.remove(checkPoint);
    }

    public void addOptionForTravel(OptionForTravel optionForTravel) {
        this.optionForTravels.add(optionForTravel);
    }

    public void removeOptionForTravel(OptionForTravel optionForTravel) {
        optionForTravels.remove(optionForTravel);
    }

    public void addTravelForClient(TravelForClient travelForClient) {
        this.travelForClients.add(travelForClient);
    }

    public void removeTravelForClient(TravelForClient travelForClient) {
        travelForClients.remove(travelForClient);
    }

    public Set<TravelForClient> getTravelForClients() {
        return travelForClients;
    }

    public List<CheckPoint> getCheckPoints() {
        return checkPoints;
    }

    public Set<OptionForTravel> getOptionForTravels() {
        return optionForTravels;
    }
}
