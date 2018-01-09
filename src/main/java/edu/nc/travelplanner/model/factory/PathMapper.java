package edu.nc.travelplanner.model.factory;

import org.springframework.stereotype.Component;

@Component
public class PathMapper {

    public String getExtension() {
        return ".json";
    }

    public String getActionPath() {
        return "\\actionJson\\";
    }

    public String getTreePath() {
        return "\\treeJson\\";
    }

    public String getSourcePath() {
        return "\\sourceJson\\";
    }

    public String getDataProducePath() { return "\\dataProducerJson\\"; }
}
