package edu.nc.travelplanner.table;

import javax.persistence.*;

@Entity
@Table(name = "travel_for_client")
public class TravelForClient {

    @Id
    @Column(name = "travel_for_client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long travelForClientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @Column(name = "description")
    private String description;

    public long getTravelForClientId() {
        return travelForClientId;
    }

    public void setTravelForClientId(long travelForClientId) {
        this.travelForClientId  = travelForClientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client  = client;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel  = travel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description  = description;
    }
}
