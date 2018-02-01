package edu.nc.travelplanner.table;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "city")
public class City {

    @Id
    @Column(name = "city_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cityId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

  //  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "city")
  //  private Set<Excursion> excursions = new HashSet<>();

  //  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "city")
  //  private Set<PlaceOfResidence> placeOfResidences = new HashSet<>();

  //  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "city")
  //  private Set<Client> clients = new HashSet<>();

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId  = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry (Country country) {
        this.country  = country;
    }

    public String toString(){
        return "City Id: " + this.cityId+" name: " + this.name +" country id: " + this.country.getCountryId();
    }

    public boolean equals(City c){
        if (this.cityId == c.getCityId())
                return true;
        return false;

    }
/*
    public void setExcursions(Set<Excursion> excursions) {
        this.excursions = excursions;
    }

    public void setPlaceOfResidences(Set<PlaceOfResidence> placeOfResidences) {
        this.placeOfResidences = placeOfResidences;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }*/
}
