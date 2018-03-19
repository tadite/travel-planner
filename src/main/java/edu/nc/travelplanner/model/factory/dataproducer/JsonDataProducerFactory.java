package edu.nc.travelplanner.model.factory.dataproducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nc.travelplanner.model.factory.filter.FilterParseException;
import edu.nc.travelplanner.model.factory.filter.ResponseFilterFactory;
import edu.nc.travelplanner.model.factory.source.SourceFactory;
import edu.nc.travelplanner.model.factory.source.SourceParseException;
import edu.nc.travelplanner.model.source.Source;
import edu.nc.travelplanner.model.source.SourceType;
import edu.nc.travelplanner.model.source.dataproducer.DataProducer;
import edu.nc.travelplanner.model.source.dataproducer.DefaultDataProducer;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import edu.nc.travelplanner.model.source.parametermapper.ParameterMapper;
import edu.nc.travelplanner.model.source.parametermapper.PickResultParameterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class JsonDataProducerFactory implements DataProducerFactory {

    private ResponseFilterFactory responseFilterFactory;
    private DataProducerJsonReader dataProducerJsonReader;
    private SourceFactory sourceFactory;
    private SenderFactory senderFactory;

    public JsonDataProducerFactory(@Autowired ResponseFilterFactory responseFilterFactory,
                                   @Autowired DataProducerJsonReader dataProducerJsonReader,
                                   @Autowired SourceFactory sourceFactory,
                                   @Autowired SenderFactory senderFactory) {
        this.responseFilterFactory = responseFilterFactory;
        this.dataProducerJsonReader = dataProducerJsonReader;
        this.sourceFactory = sourceFactory;
        this.senderFactory = senderFactory;
    }

    @Override
    public DataProducer createDataProducer(String name) throws DataProducerParseException {
        try {
            String producerJson = dataProducerJsonReader.getDataProducerJson(name);

            ObjectMapper objectMapper = new ObjectMapper();
            DataProducerDto producerDto = objectMapper.readValue(producerJson, DataProducerDto.class);

            return createDataProducerFromDto(producerDto);

        } catch (Throwable e) {
            throw new DataProducerParseException(name, e);
        }
    }

    private DataProducer createDataProducerFromDto(DataProducerDto producerDto) throws IllegalAccessException, NoSuchFieldException, SourceParseException, FilterParseException {
        DefaultDataProducer producer = new DefaultDataProducer();

        Class<? extends DefaultDataProducer> producerClass = producer.getClass();

        setSimpleFieldsByReflection(producerClass, producerDto, producer);
        setSourceAndSenderByFactory(producerClass, producerDto, producer);
        setFiltersByFactory(producerDto, producer);
        setMappersByFactory(producerDto, producer);

        return producer;
    }

    private void setMappersByFactory(DataProducerDto producerDto, DefaultDataProducer producer) throws FilterParseException {
        for (ParameterMapperDto mapperDto : producerDto.getParameterMappers()) {
            ResponseFilter responseFilter = null;
            String defaultValue = null;
            if (mapperDto.getFilterDto() != null)
                responseFilter = responseFilterFactory.create(mapperDto.getFilterDto());
            if (mapperDto.getDefaultValue() != null)
                defaultValue = mapperDto.getDefaultValue();

            producer.addParameterMapper(new PickResultParameterMapper(mapperDto.getFromKey(), mapperDto.getToKey(), responseFilter, defaultValue));
        }
    }

    private void setSenderBySource(Class<? extends DefaultDataProducer> producerClass, SourceType sourceType, DefaultDataProducer producer) throws NoSuchFieldException, IllegalAccessException {

        Field fieldToSet = producerClass.getDeclaredField("sender");
        fieldToSet.setAccessible(true);
        fieldToSet.set(producer, senderFactory.createSender(sourceType));
    }

    private void setFiltersByFactory(DataProducerDto producerDto, DefaultDataProducer producer) throws FilterParseException {
        for (ResponseFilterDto responseFilterDto : producerDto.getFilters()) {
            producer.addResponseFilter(responseFilterFactory.create(responseFilterDto));
        }
    }

    private void setSourceAndSenderByFactory(Class<? extends DefaultDataProducer> producerClass, DataProducerDto producerDto, DefaultDataProducer producer) throws NoSuchFieldException, SourceParseException, IllegalAccessException {
        Source source = sourceFactory.createSource(producerDto.getSourceName());

        Field fieldToSet = producerClass.getDeclaredField("source");
        fieldToSet.setAccessible(true);
        fieldToSet.set(producer, source);

        setSenderBySource(producerClass, source.getType(), producer);
    }

    private void setSimpleFieldsByReflection(Class<? extends DefaultDataProducer> producerClass, DataProducerDto producerDto, DefaultDataProducer producer) throws IllegalAccessException, NoSuchFieldException {
        setDataProducerName(producerClass, producer, producerDto.getName());
    }

    private void setDataProducerName(Class<? extends DefaultDataProducer> producerClass, DefaultDataProducer producer, String name) throws IllegalAccessException, NoSuchFieldException {
        Field fieldToSet = producerClass.getDeclaredField("name");
        fieldToSet.setAccessible(true);
        fieldToSet.set(producer, name);
    }
}
