package edu.nc.travelplanner.table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @Column(name = "country_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long countryId;

    @Column(name = "name")
    private String name;

  //  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "country")
   // private Set<City> cities = new HashSet<>();

  //  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "country")
  //  private Set<Excursion> excursions = new HashSet<>();

  //  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "country")
  //  private Set<PlaceOfResidence> placeOfResidences = new HashSet<>();

  //  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "country")
   // private Set<Client> clients = new HashSet<>();

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId (long countryId) {
        this.countryId  = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public String toString(){
        return "country id: " + this.countryId + " name: "+ this.name;
    }

   /* public void setCities(Set<City> cities) {
        this.cities = cities;
    }

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
