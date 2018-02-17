package edu.nc.travelplanner.model.resultsMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleFromJsonResultsMapperDtoReader implements FromJsonResultsMapperDtoReader {

    @Autowired
    PathMapper pathMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public FromJsonResultsMapperDto read(String treeName) throws IOException {

        return objectMapper.readValue(Files.readAllBytes(Paths.get(getPathToJson(treeName))), FromJsonResultsMapperDto.class);
    }

    private String getPathToJson(String treeName) {
        return PathUtil.getPathInUserDir(pathMapper.getResultMapperPath(), treeName, pathMapper.getExtension());
    }
}
