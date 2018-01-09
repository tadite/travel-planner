package edu.nc.travelplanner.model.factory.dataproducer;

import edu.nc.travelplanner.model.factory.EnumMapper;
import edu.nc.travelplanner.model.source.HttpSender;
import edu.nc.travelplanner.model.source.Sender;
import edu.nc.travelplanner.model.source.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultSenderFactory implements SenderFactory {

    private EnumMapper enumMapper;

    public DefaultSenderFactory(@Autowired EnumMapper enumMapper) {
        this.enumMapper = enumMapper;
    }

    @Override
    public Sender createSender(SourceType sourceType) {
        return enumMapper.createSender(sourceType);
    }
}
