package edu.nc.travelplanner.dao.json;

import edu.nc.travelplanner.model.factory.source.SourceDto;
import edu.nc.travelplanner.model.factory.tree.ActionTreeDto;
import edu.nc.travelplanner.model.factory.tree.JumpDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeApiDto {

    private String name;
    private String headActionName;
    private List<JumpDto> jumps;

    public TreeApiDto() {
    }

    public TreeApiDto(String name, String headActionName, List<JumpDto> jumps) {
        this.name = name;
        this.headActionName = headActionName;
        this.jumps = jumps;
    }

    public static TreeApiDto fromTreeDto(ActionTreeDto treeDto){
        TreeApiDto treeApiDto = new TreeApiDto();
        try {
            treeApiDto.setName(treeDto.getName());
            treeApiDto.setHeadActionName(treeDto.getHeadActionName());
            treeApiDto.setJumps(treeDto.getJumps());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return treeApiDto;
    }

    public ActionTreeDto toTreeDto(){
        ActionTreeDto treeDto = new ActionTreeDto();
        try {
            treeDto.setHeadActionName(this.getHeadActionName());
            treeDto.setJumps(this.getJumps());
            treeDto.setName(this.getName());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return treeDto;
    }

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
}
