package edu.nc.travelplanner.table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type_of_residence")
public class TypeOfResidence {

    @Id
    @Column(name = "type_of_residence_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long typeOfResidenceId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

  //  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "typeOfResidence")
  //  private Set<PlaceOfResidence> placeOfResidences = new HashSet<>();

    public long getTypeOfResidenceId() {
        return typeOfResidenceId;
    }

    public void setTypeOfResidenceId(long typeOfResidenceId) {
        this.typeOfResidenceId  = typeOfResidenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description  = description;
    }

   /* public void setPlaceOfResidences(Set<PlaceOfResidence> placeOfResidences) {
        this.placeOfResidences = placeOfResidences;
    }*/
}
