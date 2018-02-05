package edu.nc.travelplanner.table;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "category")
    private Set<TypeOfMovement> typeOfMovements = new HashSet<>();

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId  = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name  = name;
    }

    public void setTypeOfMovements(Set<TypeOfMovement> typeOfMovements) {
        this.typeOfMovements = typeOfMovements;
    }

    public void addTypeOfMovement(TypeOfMovement typeOfMovement) {
        this.typeOfMovements.add(typeOfMovement);
    }

    public void removeTypeOfMovement(TypeOfMovement typeOfMovement) {
        typeOfMovements.remove(typeOfMovement);
    }

    public Set<TypeOfMovement> getTypeOfMovements() {
        return typeOfMovements;
    }
}