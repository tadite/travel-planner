package edu.nc.travelplanner.dao.json.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.dao.json.JsonFileUtil;
import edu.nc.travelplanner.dao.json.SourceApiDto;
import edu.nc.travelplanner.dao.json.interfaces.SourceDao;
import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.PathUtil;
import edu.nc.travelplanner.model.factory.source.SourceDto;
import edu.nc.travelplanner.model.factory.source.SourceJsonReader;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Operation;
import org.springframework.stereotype.Component;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;


@Component
public class JsonSourceDao implements SourceDao {

    PathMapper pathMapper;
    SourceJsonReader sourceJsonReader;
    ObjectMapper objectMapper;

    @Autowired
    public JsonSourceDao(SourceJsonReader sourceJsonReader, PathMapper pathMapper, ObjectMapper objectMapper) {
        this.sourceJsonReader = sourceJsonReader;
        this.pathMapper = pathMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public SourceApiDto save(SourceApiDto dto) throws UnsupportedOperationException{
        try {
            FileUtils.writeStringToFile(new File(getPath(dto.getName())),
                    objectMapper.writeValueAsString(dto.toSourceDto()), Charset.defaultCharset(), false);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
        return get(dto.getName());
    }

    @Override
    public SourceApiDto delete(String name) throws UnsupportedOperationException {
        SourceApiDto sourceApiDto = get(name);
        try {
            JsonFileUtil.deleteJson(getPath(name));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
        return sourceApiDto;
    }

    @Override
    public SourceApiDto get(String name) throws UnsupportedOperationException {
        try {
            return SourceApiDto.fromSourceDto(objectMapper.readValue(sourceJsonReader.getSourceJson(name), SourceDto.class));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public List<SourceApiDto> getAll() throws UnsupportedOperationException {
        LinkedList<SourceApiDto> sourceApiDtos = new LinkedList<>();
        try {
            JsonFileUtil.getFileNamesFromPath(getBasePath(), pathMapper.getExtension()).forEach(name -> sourceApiDtos.add(get(name)));

        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
        return sourceApiDtos;
    }

    private String getPath(String name){
        return PathUtil.getPathInUserDir(pathMapper.getSourcePath(), name, pathMapper.getExtension());
    }

    private String getBasePath(){
        return PathUtil.getPathInUserDir(pathMapper.getSourcePath(), "", "");
    }
}
