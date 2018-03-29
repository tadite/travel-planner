package edu.nc.travelplanner.table;
import javax.persistence.*;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "name")
    private String name;

    private Long otherCityId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    public City() {
    }

    public City(String name, Country country, Long otherCityId) {
        this.name = name;
        this.otherCityId = otherCityId;
        this.country = country;
    }

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public Long getOtherCityId() {
        return otherCityId;
    }

    public void setOtherCityId(Long otherCityId) {
        this.otherCityId = otherCityId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
