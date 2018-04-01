package edu.nc.travelplanner.dao.json;

public class JumpGraphNodeDto {
    private String id;
    private String label;

    public JumpGraphNodeDto() {
    }

    public JumpGraphNodeDto(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
