package edu.nc.travelplanner.dao.json;

public class JumpGraphLinkDto {
    private String source;
    private String target;
    private String label;

    public JumpGraphLinkDto() {
    }

    public JumpGraphLinkDto(String source, String target, String label) {
        this.source = source;
        this.target = target;
        this.label = label;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
