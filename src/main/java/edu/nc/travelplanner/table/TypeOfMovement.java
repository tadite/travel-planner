package edu.nc.travelplanner.table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type_of_movement")
public class TypeOfMovement {

    @Id
    @Column(name = "type_of_movement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long typeOfMovementId;

    @Column(name = "duration")
    private String duration;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private String cost;

//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "typeOfMovement")
 //   private Set<CheckPoint> checkPoints = new HashSet<>();

    public long getTypeOfMovementId() {
        return typeOfMovementId;
    }

    public void setTypeOfMovementId(long typeOfMovementId) {
        this.typeOfMovementId  = typeOfMovementId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration  = duration;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople  = numberOfPeople;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category  = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description  = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost  = cost;
    }

  //  public void setCheckPoints(Set<CheckPoint> checkPoints) {
  //      this.checkPoints = checkPoints;
   // }
}
