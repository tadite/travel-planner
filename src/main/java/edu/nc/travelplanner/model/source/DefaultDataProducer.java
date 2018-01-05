package edu.nc.travelplanner.model.source;

import edu.nc.travelplanner.model.response.Response;
import edu.nc.travelplanner.model.source.factory.DataProducerParseException;
import edu.nc.travelplanner.model.source.factory.SenderFactory;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class DefaultDataProducer implements DataProducer{
    private Source source;
    private List<ResponseFilter> responseFilters;

    private Sender sender;

    public DefaultDataProducer(@Autowired SenderFactory senderFactory, Source source, List<ResponseFilter> responseFilters) {
        this.sender = senderFactory.createSender(source.getType());
        this.source=source;
        this.responseFilters=responseFilters;
    }

    @Override
    public Response send() throws DataProducerParseException {
        try {
            Response response = sender.send(source);

            for (ResponseFilter filter : responseFilters){
                filter.filter(response);
            }

            return response;

        } catch (IOException e) {
            throw new DataProducerParseException(e);
        }
    }
}
