package edu.nc.travelplanner.model.factory;

public class PathUtil {

    public static String getPathInUserDir(String path, String name, String extension){
        String workingDir = System.getProperty("user.dir");

        StringBuilder builder = new StringBuilder();

        builder.append(workingDir);
        builder.append(path);
        builder.append(name);
        builder.append(extension);

        return builder.toString();
    }
}
