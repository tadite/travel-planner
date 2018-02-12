package edu.nc.travelplanner.dao.json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class JsonFileUtil {
    public static List<String> getFileNamesFromPath(String path, String extension) throws IOException {
        return Files.walk(Paths.get(path))
                .filter(Files::isRegularFile)
                .map(p -> {
                    String str = p.getFileName().toString();
                    return str.substring(0, str.lastIndexOf('.'));
                })
                .collect(Collectors.toList());
    }

    public static void deleteJson(String path) throws UnsupportedOperationException{
        File file = new File(path);
        if (!file.delete())
            throw new UnsupportedOperationException();
    }

}
