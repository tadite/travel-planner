package edu.nc.travelplanner.model.source.factory;

import edu.nc.travelplanner.model.source.HttpSender;
import edu.nc.travelplanner.model.source.Sender;
import edu.nc.travelplanner.model.source.SourceType;
import org.springframework.stereotype.Component;

@Component
public class DefaultSenderFactory implements SenderFactory {
    @Override
    public Sender createSender(SourceType sourceType) {

        switch (sourceType){
            case HTTP:
                return new HttpSender();
        }

        return null;
    }
}
