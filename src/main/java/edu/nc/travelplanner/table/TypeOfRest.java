package edu.nc.travelplanner.table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type_of_rest")
public class TypeOfRest {
    @Id
    @Column(name = "type_of_rest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long typeOfRestId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "typeOfRest")
    private Set<CheckPoint> checkPoints = new HashSet<>();

    public long getTypeOfRestId() {
        return typeOfRestId;
    }

    public void setTypeOfRestId(long typeOfRestId) {
        this.typeOfRestId  = typeOfRestId;
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

    public void setCheckPoints(Set<CheckPoint> checkPoints) {
        this.checkPoints = checkPoints;
    }

    public void addCheckPoint(CheckPoint checkPoint) {
        this.checkPoints.add(checkPoint);
    }

    public void removeCheckPoint(CheckPoint checkPoint) {
        checkPoints.remove(checkPoint);
    }

    public Set<CheckPoint> getCheckPoints() {
        return checkPoints;
    }
}
