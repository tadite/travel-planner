package edu.nc.travelplanner.dao.json.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.dao.json.ActionApiDto;
import edu.nc.travelplanner.dao.json.JsonFileUtil;
import edu.nc.travelplanner.dao.json.SourceApiDto;
import edu.nc.travelplanner.dao.json.TreeApiDto;
import edu.nc.travelplanner.dao.json.interfaces.TreeDao;
import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.PathUtil;
import edu.nc.travelplanner.model.factory.action.ActionDto;
import edu.nc.travelplanner.model.factory.source.SourceJsonReader;
import edu.nc.travelplanner.model.factory.tree.ActionTreeDto;
import edu.nc.travelplanner.model.factory.tree.ActionTreeJsonReader;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

@Component
public class JsonTreeDao implements TreeDao {

    PathMapper pathMapper;
    ActionTreeJsonReader actionTreeJsonReader;
    ObjectMapper objectMapper;

    @Autowired
    public JsonTreeDao(ActionTreeJsonReader actionTreeJsonReader, PathMapper pathMapper, ObjectMapper objectMapper) {
        this.actionTreeJsonReader = actionTreeJsonReader;
        this.pathMapper = pathMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public TreeApiDto save(TreeApiDto dto) throws UnsupportedOperationException {
        try {
            FileUtils.writeStringToFile(new File(getPath(dto.getName())),
                    objectMapper.writeValueAsString(dto.toTreeDto()), Charset.defaultCharset(), false);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
        return get(dto.getName());
    }

    @Override
    public TreeApiDto delete(String name) throws UnsupportedOperationException {
        TreeApiDto treeApiDto = get(name);
        try {
            JsonFileUtil.deleteJson(getPath(name));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
        return treeApiDto;
    }

    @Override
    public TreeApiDto get(String name) throws UnsupportedOperationException {
        try {
            return TreeApiDto.fromTreeDto(objectMapper.readValue(actionTreeJsonReader.getActionTreeJson(name), ActionTreeDto.class));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public List<TreeApiDto> getAll() throws UnsupportedOperationException {
        LinkedList<TreeApiDto> treeApiDtos = new LinkedList<>();
        try {
            JsonFileUtil.getFileNamesFromPath(getBasePath(), pathMapper.getExtension()).forEach(name -> treeApiDtos.add(get(name)));

        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
        return treeApiDtos;
    }

    private String getPath(String name){
        return PathUtil.getPathInUserDir(pathMapper.getTreePath(), name, pathMapper.getExtension());
    }

    private String getBasePath(){
        return PathUtil.getPathInUserDir(pathMapper.getTreePath(), "", "");
    }
}
