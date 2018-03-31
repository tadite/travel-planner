package edu.nc.travelplanner.dao.json.implementations;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.dao.json.*;
import edu.nc.travelplanner.dao.json.interfaces.JumpDao;
import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.PathUtil;
import edu.nc.travelplanner.model.factory.action.ActionJsonReader;
import edu.nc.travelplanner.model.factory.tree.FileActionTreeJsonReader;
import edu.nc.travelplanner.model.factory.tree.JumpDto;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class JsonJumpDao implements JumpDao {

    @Value("${travelplanner.maintree}")
    private String treeName;

    @Autowired
    PathMapper pathMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FileActionTreeJsonReader actionTreeJsonReader;

    @Override
    public JumpDto save(JumpDto dto) throws UnsupportedOperationException {
        try {
            String actionTreeJson = actionTreeJsonReader.getActionTreeJson(treeName);
            TreeApiDto treeApiDto = objectMapper.readValue(actionTreeJson, TreeApiDto.class);

            treeApiDto.getJumps().add(dto);

            FileUtils.writeStringToFile(new File(getPath(treeName)), objectMapper.writeValueAsString(treeApiDto), Charset.defaultCharset(), false);
            return dto;
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public JumpDto delete(String from, String to) throws UnsupportedOperationException {
        try {
            String actionTreeJson = actionTreeJsonReader.getActionTreeJson(treeName);
            TreeApiDto treeApiDto = objectMapper.readValue(actionTreeJson, TreeApiDto.class);
            Optional<JumpDto> jumpDtoOptional = treeApiDto.getJumps().stream()
                    .filter(jumpDto -> jumpDto.getFromActionName().equals(from) && jumpDto.getToActionName().equals(to)).findFirst();
            if (jumpDtoOptional.isPresent()) {
                treeApiDto.getJumps().remove(jumpDtoOptional.get());
                FileUtils.writeStringToFile(new File(getPath(treeName)), objectMapper.writeValueAsString(treeApiDto), Charset.defaultCharset(), false);
                return jumpDtoOptional.get();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public JumpDto update(String from, String to, JumpDto dto) throws UnsupportedOperationException {
        try {
            String actionTreeJson = actionTreeJsonReader.getActionTreeJson(treeName);
            TreeApiDto treeApiDto = objectMapper.readValue(actionTreeJson, TreeApiDto.class);
            Optional<JumpDto> jumpDtoOptional = treeApiDto.getJumps().stream()
                    .filter(jumpDto -> jumpDto.getFromActionName().equals(from) && jumpDto.getToActionName().equals(to)).findFirst();
            if (jumpDtoOptional.isPresent())
                treeApiDto.getJumps().remove(jumpDtoOptional.get());
            treeApiDto.getJumps().add(dto);

            FileUtils.writeStringToFile(new File(getPath(treeName)), objectMapper.writeValueAsString(treeApiDto), Charset.defaultCharset(), false);
            return dto;
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public JumpDto get(String from, String to) throws UnsupportedOperationException {
        try {
            String actionTreeJson = actionTreeJsonReader.getActionTreeJson(treeName);
            TreeApiDto treeApiDto = objectMapper.readValue(actionTreeJson, TreeApiDto.class);
            Optional<JumpDto> jumpDtoOptional = treeApiDto.getJumps().stream()
                    .filter(jumpDto -> jumpDto.getFromActionName().equals(from) && jumpDto.getToActionName().equals(to)).findFirst();
            if (jumpDtoOptional.isPresent())
                return jumpDtoOptional.get();
            else
                throw new UnsupportedOperationException();

        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public List<JumpDto> getAll() throws UnsupportedOperationException {
        // LinkedList<JumpDto> jumpDtos = new LinkedList<>();
        try {
            String actionTreeJson = actionTreeJsonReader.getActionTreeJson(treeName);
            TreeApiDto treeApiDto = objectMapper.readValue(actionTreeJson, TreeApiDto.class);
            return treeApiDto.getJumps();
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public JumpGraphDto getJumpGraphDto() throws UnsupportedOperationException {
        List<JumpDto> allJumps = getAll();
        Set<JumpGraphNodeDto> nodes = new LinkedHashSet<>();
        List<JumpGraphLinkDto> links = new LinkedList<>();

        nodes.add(new JumpGraphNodeDto(allJumps.get(0).getToActionName(), allJumps.get(0).getToActionName()));
        allJumps.stream().forEach(jumpDto -> {
            nodes.add(new JumpGraphNodeDto(jumpDto.getToActionName(), jumpDto.getToActionName()));
            links.add(new JumpGraphLinkDto(jumpDto.getFromActionName(), jumpDto.getToActionName(), ""));
        });

        return new JumpGraphDto(new LinkedList<>(nodes), links);
    }

    private String getPath(String name) {
        return PathUtil.getPathInUserDir(pathMapper.getTreePath(), name, pathMapper.getExtension());
    }
}
