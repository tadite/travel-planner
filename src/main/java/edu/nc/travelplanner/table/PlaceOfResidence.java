package edu.nc.travelplanner.table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "place_of_residence")
public class PlaceOfResidence {
    @Id
    @Column(name = "place_of_residence_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long placeOfResidenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_of_residence_id")
    private TypeOfResidence typeOfResidence;

    @Column(name = "name")
    private String name;

    @Column(name = "climate")
    private String climate;

    @Column(name = "description")
    private String description;

    @Column(name = "cost_per_day")
    private String costPerDay;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "placeOfResidence")
    private Set<CheckPoint> checkPoints = new HashSet<>();

    public long getPlaceOfResidenceId() {
        return placeOfResidenceId;
    }

    public void setPlaceOfResidenceId(long placeOfResidenceId) {
        this.placeOfResidenceId  = placeOfResidenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city  = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry (Country country) {
        this.country  = country;
    }

    public TypeOfResidence getTypeOfResidence() {
        return typeOfResidence;
    }

    public void setTypeOfResidence(TypeOfResidence typeOfResidence) {
        this.typeOfResidence  = typeOfResidence;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate  = climate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description  = description;
    }

    public String getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(String costPerDay) {
        this.costPerDay  = costPerDay;
    }

    public void setCheckPoints(Set<CheckPoint> checkPoints) {
        this.checkPoints = checkPoints;
    }

    public void addCheckPoint(CheckPoint checkPoint) {
        this.checkPoints.add(checkPoint);
    }

    public void removeCheckPoint(CheckPoint checkPoint) {
        checkPoints.remove(checkPoint);
    }

    public Set<CheckPoint> getCheckPoints() {
        return checkPoints;
    }
}
