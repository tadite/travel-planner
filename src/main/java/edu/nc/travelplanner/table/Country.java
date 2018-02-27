package edu.nc.travelplanner.table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "country")
    private Set<City> cities = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "country")
    private Set<Excursion> excursions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "country")
    private Set<PlaceOfResidence> placeOfResidences = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "country")
    private Set<Client> clients = new HashSet<>();

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId (Integer countryId) {
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

    public void setCities(Set<City> cities) {
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
    }

    public void addExcursion(Excursion excursion) {
        this.excursions.add(excursion);
    }

    public void removeExcursion(Excursion excursion) {
        excursions.remove(excursion);
    }

    public void addCity(City city) {
        this.cities.add(city);
    }

    public void removeCity(City city) {
        cities.remove(city);
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    public void addPlaceOfResidence(PlaceOfResidence placeOfResidence) {
        this.placeOfResidences.add(placeOfResidence);
    }

    public void removePlaceOfResidence(PlaceOfResidence placeOfResidence) {
        placeOfResidences.remove(placeOfResidence);
    }

    public Set<City> getCities() {
        return cities;
    }

    public Set<Excursion> getExcursions() {
        return excursions;
    }

    public Set<PlaceOfResidence> getPlaceOfResidences() {
        return placeOfResidences;
    }

    public Set<Client> getClients() {
        return clients;
    }
}
