package edu.nc.travelplanner.model.factory;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.jump.JumpType;
import edu.nc.travelplanner.model.source.FilterType;
import edu.nc.travelplanner.model.source.Sender;
import edu.nc.travelplanner.model.source.Source;
import edu.nc.travelplanner.model.source.SourceType;
import edu.nc.travelplanner.model.source.filter.ResponseFilter;

public interface EnumMapper {
    Jump create(JumpType type);
    Action create(ActionType type);
    ResponseFilter create(FilterType type);
    Source create(SourceType type);
    Sender createSender(SourceType type);
}
