package edu.nc.travelplanner.model.source.factory;

import edu.nc.travelplanner.model.source.Sender;
import edu.nc.travelplanner.model.source.SourceType;

public interface SenderFactory {
    Sender createSender(SourceType sourceType);
}
