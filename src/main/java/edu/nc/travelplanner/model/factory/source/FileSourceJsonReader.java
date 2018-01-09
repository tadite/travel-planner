package edu.nc.travelplanner.model.factory.source;

import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileSourceJsonReader implements SourceJsonReader {

    @Autowired
    private PathMapper pathMapper;

    @Override
    public String getSourceJson(String name) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getPathToJson(name))));
    }

    private String getPathToJson(String name) {
        return PathUtil.getPathInUserDir(pathMapper.getSourcePath(), name, pathMapper.getExtension());
    }
}
