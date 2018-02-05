package edu.nc.travelplanner.table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "option")
public class Option {
    @Id
    @Column(name = "option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long optionId;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "option")
    private Set<OptionForTravel> optionForTravels = new HashSet<>();

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId  = optionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public void setOptionForTravels(Set<OptionForTravel> optionForTravels) {
        this.optionForTravels = optionForTravels;
    }

    public void addOptionForTravel(OptionForTravel optionForTravel) {
        this.optionForTravels.add(optionForTravel);
    }

    public void removeOptionForTravel(OptionForTravel optionForTravel) {
        optionForTravels.remove(optionForTravel);
    }

    public Set<OptionForTravel> getOptionForTravels() {
        return optionForTravels;
    }
}
