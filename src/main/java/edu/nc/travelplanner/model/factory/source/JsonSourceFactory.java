package edu.nc.travelplanner.model.factory.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.factory.EnumMapper;
import edu.nc.travelplanner.model.source.Source;
import edu.nc.travelplanner.model.source.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class JsonSourceFactory implements SourceFactory {

    private SourceJsonReader sourceJsonReader;
    private EnumMapper enumMapper;

    public JsonSourceFactory(@Autowired SourceJsonReader sourceJsonReader,
                             @Autowired EnumMapper enumMapper) {
        this.sourceJsonReader = sourceJsonReader;
        this.enumMapper = enumMapper;
    }

    @Override
    public Source createSource(String name) throws SourceParseException {
        try {
            String sourceJson = sourceJsonReader.getSourceJson(name);

            ObjectMapper objectMapper = new ObjectMapper();
            SourceDto sourceDto = objectMapper.readValue(sourceJson, SourceDto.class);

            return createSourceFromDto(sourceDto);

        } catch (Exception e) {
            throw new SourceParseException(name,e);
        }
    }

    private Source createSourceFromDto(SourceDto sourceDto)
            throws NoSuchFieldException, IllegalAccessException{
        Source source = createSourceByType(sourceDto.getType());

        setFieldsByReflection(sourceDto, source);

        return source;
    }

    private void setSourceType(Class<? extends Source> sourceClass, Source source, SourceType type)
            throws NoSuchFieldException, IllegalAccessException {
        Field fieldToSet = sourceClass.getDeclaredField("type");
        fieldToSet.setAccessible(true);
        fieldToSet.set(source, type);
    }

    private void setSourceName(Class<? extends Source> sourceClass, Source source, String name)
            throws NoSuchFieldException, IllegalAccessException {
        Field fieldToSet = sourceClass.getDeclaredField("name");
        fieldToSet.setAccessible(true);
        fieldToSet.set(source, name);
    }

    private void setFieldsByReflection(SourceDto sourceDto, Source source)
            throws NoSuchFieldException, IllegalAccessException{

        setSourceType(source.getClass() ,source, sourceDto.getType());
        setSourceName(source.getClass() ,source, sourceDto.getName());
        setSourceParameters(source.getClass() ,source, sourceDto.getParameters());

    }

    private Source createSourceByType(SourceType type) {
        return enumMapper.create(type);
    }

    private void setSourceParameters(Class<? extends Source> sourceClass, Source source,
                                     Map<String, Object> parameters)
            throws IllegalAccessException, NoSuchFieldException {
        for (Map.Entry<String,Object> entry : parameters.entrySet()){
            Field fieldToSet = sourceClass.getDeclaredField(entry.getKey());
            fieldToSet.setAccessible(true);
            fieldToSet.set(source, entry.getValue());
        }
    }
}
