package edu.nc.travelplanner.model.factory.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.factory.EnumMapper;
import edu.nc.travelplanner.model.factory.dataproducer.ResponseFilterDto;
import edu.nc.travelplanner.model.source.FilterType;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class DefaultResponseFilterFactory implements ResponseFilterFactory {

    private EnumMapper enumMapper;

    public DefaultResponseFilterFactory(@Autowired EnumMapper enumMapper) {

        this.enumMapper = enumMapper;
    }

    @Override
    public ResponseFilter create(ResponseFilterDto responseFilterDto) throws FilterParseException {
        try {
            return createFilterFromDto(responseFilterDto);

        } catch (Exception e) {
            throw new FilterParseException(e);
        }
    }

    private ResponseFilter createFilterFromDto(ResponseFilterDto responseFilterDto)
            throws NoSuchFieldException, IllegalAccessException{
        ResponseFilter filter = createFilterByType(responseFilterDto.getType());

        setFieldsByReflection(responseFilterDto, filter);

        return filter;
    }



    private void setSourceType(Class<? extends ResponseFilter> filterClass, ResponseFilter filter, FilterType type)
            throws NoSuchFieldException, IllegalAccessException {
        Field fieldToSet = filterClass.getDeclaredField("type");
        fieldToSet.setAccessible(true);
        fieldToSet.set(filter, type);
    }

    private void setFieldsByReflection(ResponseFilterDto responseFilterDto, ResponseFilter filter)
            throws NoSuchFieldException, IllegalAccessException{

        setSourceType(filter.getClass() ,filter, responseFilterDto.getType());
        setSourceParameters(filter.getClass() ,filter, responseFilterDto.getParameters());

    }

    private ResponseFilter createFilterByType(FilterType type) {
        return enumMapper.create(type);
    }

    private void setSourceParameters(Class<? extends ResponseFilter> filterClass, ResponseFilter filter,
                                     Map<String, Object> parameters)
            throws IllegalAccessException, NoSuchFieldException {
        for (Map.Entry<String,Object> entry : parameters.entrySet()){
            Field fieldToSet = filterClass.getDeclaredField(entry.getKey());
            fieldToSet.setAccessible(true);
            fieldToSet.set(filter, entry.getValue());
        }
    }
}
