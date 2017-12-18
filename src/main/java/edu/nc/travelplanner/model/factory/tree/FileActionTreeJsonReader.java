package edu.nc.travelplanner.model.factory.tree;

import edu.nc.travelplanner.model.factory.PathUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileActionTreeJsonReader implements ActionTreeJsonReader {

    private final String pathToActions = "\\treeJson\\";
    private final String actionFileExtension = ".json";

    @Override
    public String getActionTreeJson(String name) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getPathToJson(name))));
    }

    private String getPathToJson(String name) {
        return PathUtil.getPathInUserDir(pathToActions, name, actionFileExtension);
    }
}
