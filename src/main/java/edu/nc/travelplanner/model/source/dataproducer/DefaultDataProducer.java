package edu.nc.travelplanner.model.source.dataproducer;

import edu.nc.travelplanner.exception.DataProducerSendException;
import edu.nc.travelplanner.exception.NotEnoughParamsException;
import edu.nc.travelplanner.model.action.PickResult;
import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.parametermapper.ParameterMapper;
import edu.nc.travelplanner.model.source.Sender;
import edu.nc.travelplanner.model.source.Source;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;

import java.io.IOException;
import java.util.*;

public class DefaultDataProducer implements DataProducer{

    private String name;
    private Source source;
    private List<ResponseFilter> responseFilters = new LinkedList<>();
    private List<ParameterMapper> parameterMappers = new LinkedList<>();

    private Sender sender;

    public DefaultDataProducer() {
    }

    public DefaultDataProducer(Source source, List<ResponseFilter> responseFilters) {
        this.source = source;
        this.responseFilters = responseFilters;
    }

    public DefaultDataProducer(Sender sender, Source source, List<ResponseFilter> responseFilters) {
        this.sender = sender;
        this.source=source;
        this.responseFilters=responseFilters;
    }

    private Sender getSender(){
        return sender;
    }

    @Override
    public Response send(List<PickResult> pickResults) throws DataProducerSendException, NotEnoughParamsException {
        try {
            mapParameters(pickResults);

            Response response = getSender().send(source);

            for (ResponseFilter filter : responseFilters){
                filter.filter(response);
            }

            return response;

        } catch (IOException e) {
            throw new DataProducerSendException(e);
        }
    }

    private void mapParameters(List<PickResult> pickResults) throws IOException {
        Map<String, String> tempParameterValues = new LinkedHashMap<>();
        for (ParameterMapper parameterMapper : parameterMappers) {
            if (tempParameterValues.containsKey(parameterMapper.getToKey())) {
                source.addParameterValue(parameterMapper.getToKey(), tempParameterValues.get(parameterMapper.getToKey()));
                continue;
            }

            Optional<PickResult> pickResultOptional = pickResults.stream()
                    .filter(pickResult -> pickResult.getKey().equals(parameterMapper.getFromKey()))
                    .findFirst();

            if (pickResultOptional.isPresent())
                source.addParameterValue(parameterMapper.getToKey(), parameterMapper.filterValue(
                        String.valueOf(pickResultOptional.get().getValue()),
                        tempParameterValues));
            else if (parameterMapper.getDefaultValue()!=null)
                source.addParameterValue(parameterMapper.getToKey(), parameterMapper.getDefaultValue());

        }
    }

    public String getName() {
        return name;
    }

    public Source getSource() {
        return source;
    }

    public List<ResponseFilter> getResponseFilters() {
        return responseFilters;
    }

    public void addResponseFilter(ResponseFilter responseFilter){
        this.responseFilters.add(responseFilter);
    }

    public void addParameterMapper(ParameterMapper parameterMapper){
        this.parameterMappers.add(parameterMapper);
    }
}
