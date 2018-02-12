package edu.nc.travelplanner.dao.json.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.dao.json.DataProducerApiDto;
import edu.nc.travelplanner.dao.json.JsonFileUtil;
import edu.nc.travelplanner.dao.json.SourceApiDto;
import edu.nc.travelplanner.dao.json.interfaces.DataProducerDao;
import edu.nc.travelplanner.model.factory.PathMapper;
import edu.nc.travelplanner.model.factory.PathUtil;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerDto;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerJsonReader;
import edu.nc.travelplanner.model.factory.source.SourceDto;
import edu.nc.travelplanner.model.factory.source.SourceJsonReader;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

@Component
public class JsonDataProducerDao implements DataProducerDao {

    PathMapper pathMapper;
    DataProducerJsonReader dataProducerJsonReader;
    ObjectMapper objectMapper;

    @Autowired
    public JsonDataProducerDao(DataProducerJsonReader dataProducerJsonReader, PathMapper pathMapper, ObjectMapper objectMapper) {
        this.dataProducerJsonReader = dataProducerJsonReader;
        this.pathMapper = pathMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public DataProducerApiDto save(DataProducerApiDto dto) throws UnsupportedOperationException {
        try {
            FileUtils.writeStringToFile(new File(getPath(dto.getName())),
                    objectMapper.writeValueAsString(dto.toDataProducerDto()), Charset.defaultCharset(), false);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
        return get(dto.getName());
    }

    @Override
    public DataProducerApiDto delete(String name) throws UnsupportedOperationException {
        DataProducerApiDto dataProducerApiDto = get(name);
        try {
            JsonFileUtil.deleteJson(getPath(name));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
        return dataProducerApiDto;
    }

    @Override
    public DataProducerApiDto get(String name) throws UnsupportedOperationException {
        try {
            return DataProducerApiDto.fromDataProducerDto(objectMapper.readValue(dataProducerJsonReader.getDataProducerJson(name), DataProducerDto.class));
        }
        catch (Exception e){
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public List<DataProducerApiDto> getAll() throws UnsupportedOperationException {
        LinkedList<DataProducerApiDto> dataProducerApiDtos = new LinkedList<>();
        try {
            JsonFileUtil.getFileNamesFromPath(getBasePath(), pathMapper.getExtension()).forEach(name -> dataProducerApiDtos.add(get(name)));

        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
        return dataProducerApiDtos;
    }

    private String getBasePath() {
        return PathUtil.getPathInUserDir(pathMapper.getDataProducePath(), "", "");
    }

    private String getPath(String name) {
        return PathUtil.getPathInUserDir(pathMapper.getDataProducePath(), name, pathMapper.getExtension());
    }
}
