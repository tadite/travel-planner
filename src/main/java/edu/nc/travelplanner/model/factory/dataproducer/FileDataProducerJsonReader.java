package edu.nc.travelplanner.model.factory.dataproducer;

import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileDataProducerJsonReader implements DataProducerJsonReader {

    @Autowired
    private PathMapper pathMapper;

    private String getPathToJson(String name) {
        return PathUtil.getPathInUserDir(pathMapper.getDataProducePath(), name, pathMapper.getExtension());
    }

    @Override
    public String getDataProducerJson(String name) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getPathToJson(name))));
    }
}
