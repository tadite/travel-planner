package edu.nc.travelplanner.table;



import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Table(name = "excursion")
public class Excursion {
    @Id
    @Column(name = "excursion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long excursionId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "duration")
    private String duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_point_id")
    private CheckPoint checkPoint;

    @Column(name = "cost")
    private String cost;


    public long getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(long excursionId) {
        this.excursionId  = excursionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name  = name;
    }

    @Transactional
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city  = city;
    }

    @Transactional
    public Country getCountry() {
        return country;
    }

    public void setCountry (Country country) {
        this.country  = country;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration  = duration;
    }

    @Transactional
    public CheckPoint getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(CheckPoint checkPoint) {
        this.checkPoint  = checkPoint;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost  = cost;
    }

}

