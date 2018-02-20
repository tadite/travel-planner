package edu.nc.travelplanner.model.factory.action;

import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileActionJsonReader implements ActionJsonReader {

    private PathMapper pathMapper;

    @Autowired
    public FileActionJsonReader(PathMapper pathMapper) {
        this.pathMapper = pathMapper;
    }

    @Override
    public String getActionJson(String name) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getPathToJson(name))));
    }

    private String getPathToJson(String name) {
        return PathUtil.getPathInUserDir(pathMapper.getActionPath(), name, pathMapper.getExtension());
    }
}
