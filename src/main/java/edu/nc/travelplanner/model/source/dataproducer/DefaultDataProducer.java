package edu.nc.travelplanner.model.source.dataproducer;

import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.factory.dataproducer.DataProducerParseException;
import edu.nc.travelplanner.model.factory.dataproducer.SenderFactory;
import edu.nc.travelplanner.model.source.Sender;
import edu.nc.travelplanner.model.source.Source;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DefaultDataProducer implements DataProducer{

    private String name;
    private Source source;
    private List<ResponseFilter> responseFilters = new LinkedList<>();

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
    public Response send() throws DataProducerParseException {
        try {
            Response response = getSender().send(source);

            for (ResponseFilter filter : responseFilters){
                filter.filter(response);
            }

            return response;

        } catch (IOException e) {
            throw new DataProducerParseException(e);
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
}
