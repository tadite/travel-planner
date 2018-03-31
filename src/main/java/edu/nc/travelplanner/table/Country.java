package edu.nc.travelplanner.table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "name")
    private String name;

    private Integer otherCountryId;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "country")
    private List<City> cities = new LinkedList<>();

    public Country() {
    }

    public Integer getOtherCountryId() {
        return otherCountryId;
    }

    public void setOtherCountryId(Integer otherCountryId) {
        this.otherCountryId = otherCountryId;
    }

    public Country(String name) {
        this.name = name;
    }

    public Country(String name, Integer otherCountryId) {
        this.name = name;
        this.otherCountryId = otherCountryId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
