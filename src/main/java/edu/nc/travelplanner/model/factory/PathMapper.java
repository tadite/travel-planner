package edu.nc.travelplanner.model.factory;

import org.springframework.stereotype.Component;

@Component
public class PathMapper {

    public String getExtension() {
        return ".json";
    }

    public String getActionPath() {
        return "/json/action/";
    }

    public String getTreePath() {
        return "/json/tree/";
    }

    public String getSourcePath() {
        return "/json/source/";
    }

    public String getDataProducePath() { return "/json/dataProducer/"; }

    public String getResultMapperPath() { return "/json/resultMapper/"; }

    public String getResultMapperConfigPath() { return "/json/data-producers-config.json"; }
}
