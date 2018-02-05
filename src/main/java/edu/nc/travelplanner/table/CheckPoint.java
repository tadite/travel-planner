package edu.nc.travelplanner.table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "check_point")
public class CheckPoint {
    @Id
    @Column(name = "check_point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long checkPointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "cost")
    private String cost;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_of_rest_id")
    private TypeOfRest typeOfRest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_of_movement_id")
    private TypeOfMovement typeOfMovement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_of_residence_id")
    private PlaceOfResidence placeOfResidence;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "checkPoint")
    private Set<Excursion> excursions = new HashSet<>();

    public long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(long checkPointId) {
        this.checkPointId  = checkPointId;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel  = travel;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays  = numberOfDays;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost  = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description  = description;
    }

    public TypeOfRest getTypeOfRest() {
        return typeOfRest;
    }

    public void setTypeOfRest(TypeOfRest typeOfRest) {
        this.typeOfRest  = typeOfRest;
    }

    public TypeOfMovement getTypeOfMovement() {
        return typeOfMovement;
    }

    public void setTypeOfMovement(TypeOfMovement typeOfMovement) {
        this.typeOfMovement  = typeOfMovement;
    }

    public PlaceOfResidence getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(PlaceOfResidence placeOfResidence) {
        this.placeOfResidence  = placeOfResidence;
    }

    public String toString(){
        return "Check Point id: " + this.checkPointId + " travel: " +this.travel+" numberOfDays: "+ this.numberOfDays+" cost: " + this.cost+" description: " + this.description +" typeOfRest id: " +typeOfRest.getTypeOfRestId()+" typeOfMovement id: " + typeOfMovement.getTypeOfMovementId()+ " placeOfResidence id: " + placeOfResidence.getPlaceOfResidenceId();
    }

    public void setExcursions(Set<Excursion> excursions) {
        this.excursions = excursions;
    }

    public void addExcursion(Excursion excursion) {
        this.excursions.add(excursion);
    }

    public void removeExcursion(Excursion excursion) {
        excursions.remove(excursion);
    }

    public Set<Excursion> getExcursions() {
        return excursions;
    }
}
