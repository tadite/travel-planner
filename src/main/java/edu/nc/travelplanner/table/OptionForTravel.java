package edu.nc.travelplanner.table;

import javax.persistence.*;

@Entity
@Table(name = "option_for_travel")
public class OptionForTravel {
    @Id
    @Column(name = "option_for_travel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long optionForTravelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

    @Column(name = "description")
    private String description;

    public long getOptionForTravelId() {
        return optionForTravelId;
    }

    public void setOptionForTravelId(long optionForTraveld) {
        this.optionForTravelId  = optionForTraveld;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel  = travel;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option  = option;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description  = description;
    }
}
