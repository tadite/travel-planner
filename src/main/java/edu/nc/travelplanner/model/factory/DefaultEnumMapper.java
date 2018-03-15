package edu.nc.travelplanner.model.factory;

import edu.nc.travelplanner.model.action.Action;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.action.constant.*;
import edu.nc.travelplanner.model.action.source.CheckListIntegrationAction;
import edu.nc.travelplanner.model.action.source.DropDownListIntegrationAction;
import edu.nc.travelplanner.model.action.source.InfoIntegrationAction;
import edu.nc.travelplanner.model.action.source.RadioListIntegrationAction;
import edu.nc.travelplanner.model.jump.Jump;
import edu.nc.travelplanner.model.jump.JumpType;
import edu.nc.travelplanner.model.jump.LogicConditionOnPickResultJump;
import edu.nc.travelplanner.model.jump.NoConditionJump;
import edu.nc.travelplanner.model.source.*;
import edu.nc.travelplanner.model.source.filter.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class DefaultEnumMapper implements EnumMapper {

    private Map<JumpType, Supplier<Jump>> jumpCreationMap = new HashMap<JumpType, Supplier<Jump>>(){{
        put(JumpType.WITHOUT_CONDITION, NoConditionJump::new);
        put(JumpType.LOGIC_CONDITION_ON_PICK_RESULT, LogicConditionOnPickResultJump::new);
    }};

    private Map<ActionType, Supplier<Action>> actionCreationMap = new HashMap<ActionType, Supplier<Action>>(){{
        put(ActionType.INFO, InfoAction::new);
        put(ActionType.CHECKLIST, CheckListAction::new);
        put(ActionType.RADIOLIST, RadioListAction::new);
        put(ActionType.TEXT_INPUT, TextInputAction::new);
        put(ActionType.DROPDOWN_INPUT, DropDownListAction::new);
        put(ActionType.DATE_INTERVAL_INPUT, DateIntervalInputAction::new);

        put(ActionType.DROPDOWN_INTEGRATION, DropDownListIntegrationAction::new);
        put(ActionType.CHECKLIST_INTEGRATION, CheckListIntegrationAction::new);
        put(ActionType.INFO_INTEGRATION, InfoIntegrationAction::new);

        put(ActionType.RADIOLIST_INTEGRATION, RadioListIntegrationAction::new);
    }};

    private Map<FilterType, Supplier<ResponseFilter>> filterCreationMap = new HashMap<FilterType, Supplier<ResponseFilter>>(){{
        put(FilterType.JSON_PATH, JsonPathResponseFilter::new);
        put(FilterType.LIST_TO_MAP, ListToMapJsonResponseFilter::new);
        put(FilterType.LIST_TO_MAP_MULTIPLE, ListToMapMultipleJsonResponseFilter::new);
        put(FilterType.REGEXP_REPLACE, RegexpReplaceAllResponseFilter::new);
        put(FilterType.SUBSTRING, SubstringResponseFilter::new);
    }};

    private Map<SourceType, Supplier<Source>> sourceCreationMap = new HashMap<SourceType, Supplier<Source>>(){{
        put(SourceType.HTTP, HttpSource::new);
    }};

    private Map<SourceType, Supplier<Sender>> senderCreationMap = new HashMap<SourceType, Supplier<Sender>>(){{
        put(SourceType.HTTP, HttpSender::new);
    }};


    @Override
    public Jump create(JumpType type) {
        return jumpCreationMap.get(type).get();
    }

    @Override
    public Action create(ActionType type) {
        return actionCreationMap.get(type).get();
    }

    @Override
    public ResponseFilter create(FilterType type) {
        return filterCreationMap.get(type).get();
    }

    @Override
    public Source create(SourceType type) {
        return sourceCreationMap.get(type).get();
    }

    @Override
    public Sender createSender(SourceType type) { return senderCreationMap.get(type).get(); }
}
