package edu.nc.travelplanner.model.factory;

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
        String workingDir = System.getProperty("user.dir");

        StringBuilder builder = new StringBuilder();

        builder.append(workingDir);
        builder.append(pathToActions);
        builder.append(name);
        builder.append(actionFileExtension);

        return builder.toString();
    }
}
