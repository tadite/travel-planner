package edu.nc.travelplanner.model.resultsMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class JsonFileResultsMapperReader implements ResultsMapperReader {

    @Autowired
    PathMapper pathMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public ResultsMapper read(String treeName) throws IOException {

        FromJsonResultsMapperDto fromJsonResultsMapperDto = objectMapper.readValue(Files.readAllBytes(Paths.get(getPathToJson(treeName))), FromJsonResultsMapperDto.class);

        return new FromJsonResultsMapper(fromJsonResultsMapperDto.getMap());
    }

    private String getPathToJson(String treeName) {
        return PathUtil.getPathInUserDir(pathMapper.getResultMapperPath(), treeName, pathMapper.getExtension());
    }
}
