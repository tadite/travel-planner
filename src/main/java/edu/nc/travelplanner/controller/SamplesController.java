package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.dao.json.ActionApiDto;
import edu.nc.travelplanner.dao.json.DataProducerApiDto;
import edu.nc.travelplanner.model.action.ActionType;
import edu.nc.travelplanner.model.factory.dataproducer.ResponseFilterDto;
import edu.nc.travelplanner.model.source.FilterType;
import edu.nc.travelplanner.model.source.filter.JsonPathResponseFilter;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sample")
public class SamplesController {

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    @ResponseBody
    public List<ResponseFilterDto> getFilters() {
        List<ResponseFilterDto> responseFilterDtos = new LinkedList<>();

        responseFilterDtos.add(new ResponseFilterDto(FilterType.JSON_PATH, new HashMap<String, Object>() {{put("expression", "$.response[*]");}}));
        responseFilterDtos.add(new ResponseFilterDto(FilterType.LIST_TO_MAP, new HashMap<String, Object>() {{put("keyName", "cid");put("valueName", "title");}}));
        responseFilterDtos.add(new ResponseFilterDto(FilterType.REGEXP_REPLACE, new HashMap<String, Object>() {{put("regex", "[a-zA-Z]+");put("replacement", "Java");}}));
        responseFilterDtos.add(new ResponseFilterDto(FilterType.SUBSTRING, new HashMap<String, Object>() {{put("firstIndexStr", "[");put("lastIndexStr", "]");}}));

        return responseFilterDtos;
    }

    @RequestMapping(value = "/action", method = RequestMethod.GET)
    @ResponseBody
    public List<ActionApiDto> getActions() {
        List<ActionApiDto> actionApiDtos = new LinkedList<>();

        actionApiDtos.add(new ActionApiDto("testActionName", ActionType.CHECKLIST_INTEGRATION, new HashMap<String, Object>(){{}}, "dataProducerName"));
        actionApiDtos.add(new ActionApiDto("testActionName", ActionType.DROPDOWN_INTEGRATION, new HashMap<String, Object>(){{}}, "dataProducerName"));
        actionApiDtos.add(new ActionApiDto("testActionName", ActionType.INFO_INTEGRATION, new HashMap<String, Object>(){{}}, "dataProducerName"));

        return actionApiDtos;
    }

}
