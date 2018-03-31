package edu.nc.travelplanner.dao.json;

import java.util.List;

public class JumpGraphDto {
    private List<JumpGraphNodeDto> nodes;
    private List<JumpGraphLinkDto> links;

    public JumpGraphDto(List<JumpGraphNodeDto> nodes, List<JumpGraphLinkDto> links) {
        this.nodes = nodes;
        this.links = links;
    }

    public List<JumpGraphNodeDto> getNodes() {
        return nodes;
    }

    public void setNodes(List<JumpGraphNodeDto> nodes) {
        this.nodes = nodes;
    }

    public List<JumpGraphLinkDto> getLinks() {
        return links;
    }

    public void setLinks(List<JumpGraphLinkDto> links) {
        this.links = links;
    }
}
