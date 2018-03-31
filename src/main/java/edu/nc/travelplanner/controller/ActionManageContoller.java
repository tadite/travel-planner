package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.dao.json.ActionApiDto;
import edu.nc.travelplanner.dao.json.SourceApiDto;
import edu.nc.travelplanner.dao.json.interfaces.ActionDao;
import edu.nc.travelplanner.dao.json.interfaces.SourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/manage/action")
public class ActionManageContoller {

    private ActionDao actionDao;

    @Autowired
    public ActionManageContoller(ActionDao actionDao) {
        this.actionDao = actionDao;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ActionApiDto save(@RequestBody ActionApiDto sourceApiDto){
        ActionApiDto actionApiDto = new ActionApiDto();
        actionApiDto.setViewName(sourceApiDto.getViewName());
        actionApiDto.setType(sourceApiDto.getType());
        actionApiDto.setName(sourceApiDto.getName());
        actionApiDto.setDataProducerName(sourceApiDto.getDataProducerName());
        List<Map<String, Object>> columnDefsList = (List<Map<String, Object>>) sourceApiDto.getParameters().get("columnDefs");
        Map<Object, Object> newColumnDefs = new LinkedHashMap<>();
        columnDefsList.forEach(map -> newColumnDefs.put(map.get("key"),map.get("title")));

        sourceApiDto.getParameters().put("columnDefs", newColumnDefs);
        return actionDao.save(sourceApiDto);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    public ActionApiDto get(@PathVariable(value = "name") String name) {
        return actionDao.get(name);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<ActionApiDto> getAll() {
        return actionDao.getAll();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    @ResponseBody
    public ActionApiDto delete(@PathVariable(value = "name") String name){
        return actionDao.delete(name);
    }
}
