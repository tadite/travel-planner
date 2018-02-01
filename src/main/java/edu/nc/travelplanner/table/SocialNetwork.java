package edu.nc.travelplanner.table;

import javax.persistence.*;

@Entity
@Table(name = "social_network")
public class SocialNetwork {

    @Id
    @Column(name = "social_network_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long socialNetworkId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public long getSocialNetworkId() {
        return socialNetworkId;
    }

    public void setSocialNetworkId(long socialNetworkId) {
        this.socialNetworkId  = socialNetworkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client  = client;
    }
}
