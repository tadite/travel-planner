package edu.nc.travelplanner.model.factory.tree;

import java.util.List;

public class ActionTreeDto {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadActionName() {
        return headActionName;
    }

    public void setHeadActionName(String headActionName) {
        this.headActionName = headActionName;
    }

    public List<JumpDto> getJumps() {
        return jumps;
    }

    public void setJumps(List<JumpDto> jumps) {
        this.jumps = jumps;
    }

    private String name;
    private String headActionName;
    private List<JumpDto> jumps;
}
