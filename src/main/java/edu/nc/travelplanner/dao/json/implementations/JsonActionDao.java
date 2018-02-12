package edu.nc.travelplanner.dao.json.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.dao.json.ActionApiDto;
import edu.nc.travelplanner.dao.json.JsonFileUtil;
import edu.nc.travelplanner.dao.json.SourceApiDto;
import edu.nc.travelplanner.dao.json.interfaces.ActionDao;
import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.PathUtil;
import edu.nc.travelplanner.model.factory.action.ActionDto;
import edu.nc.travelplanner.model.factory.action.ActionJsonReader;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerJsonReader;
import edu.nc.travelplanner.model.factory.source.SourceDto;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

@Component
public class JsonActionDao implements ActionDao {

    PathMapper pathMapper;
    ActionJsonReader actionJsonReader;
    ObjectMapper objectMapper;

    @Autowired
    public JsonActionDao(ActionJsonReader actionJsonReader, PathMapper pathMapper, ObjectMapper objectMapper) {
        this.actionJsonReader = actionJsonReader;
        this.pathMapper = pathMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public ActionApiDto save(ActionApiDto dto) throws UnsupportedOperationException {
        try {
            FileUtils.writeStringToFile(new File(getPath(dto.getName())),
                    objectMapper.writeValueAsString(dto.toActionDto()), Charset.defaultCharset(), false);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
        return get(dto.getName());
    }

    @Override
    public ActionApiDto delete(String name) throws UnsupportedOperationException {
        ActionApiDto actionApiDto = get(name);
        try {
            JsonFileUtil.deleteJson(getPath(name));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
        return actionApiDto;
    }

    @Override
    public ActionApiDto get(String name) throws UnsupportedOperationException {
        try {
            return ActionApiDto.fromActionDto(objectMapper.readValue(actionJsonReader.getActionJson(name), ActionDto.class));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public List<ActionApiDto> getAll() throws UnsupportedOperationException {
        LinkedList<ActionApiDto> actionApiDtos = new LinkedList<>();
        try {
            JsonFileUtil.getFileNamesFromPath(getBasePath(), pathMapper.getExtension()).forEach(name -> actionApiDtos.add(get(name)));

        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
        return actionApiDtos;
    }

    private String getPath(String name){
        return PathUtil.getPathInUserDir(pathMapper.getActionPath(), name, pathMapper.getExtension());
    }

    private String getBasePath(){
        return PathUtil.getPathInUserDir(pathMapper.getActionPath(), "", "");
    }
}
