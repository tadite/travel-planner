package edu.nc.travelplanner.table;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "city")
public class City {
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "city_id")
    private long cityId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "city")
    private Set<Excursion> excursions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "city")
    private Set<PlaceOfResidence> placeOfResidences = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "city")
    private Set<Client> clients = new HashSet<>();

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

    public void addPlaceOfResidence(PlaceOfResidence placeOfResidence) {
        this.placeOfResidences.add(placeOfResidence);
    }

    public void removePlaceOfResidence(PlaceOfResidence placeOfResidence) {
        placeOfResidences.remove(placeOfResidence);
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }

    public void removeClient(Client client) {
        clients.remove(client);
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
