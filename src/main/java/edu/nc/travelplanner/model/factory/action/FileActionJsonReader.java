package edu.nc.travelplanner.model.factory.action;

import edu.nc.travelplanner.model.factory.PathUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileActionJsonReader implements ActionJsonReader {

    private final String pathToActions = "\\actionJson\\";
    private final String actionFileExtension = ".json";

    @Override
    public String getActionJson(String name) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getPathToJson(name))));
    }

    private String getPathToJson(String name) {
        return PathUtil.getPathInUserDir(pathToActions, name, actionFileExtension);
    }
}
