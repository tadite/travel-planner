package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.dao.json.ActionApiDto;
import edu.nc.travelplanner.dao.json.SourceApiDto;
import edu.nc.travelplanner.dao.json.interfaces.ActionDao;
import edu.nc.travelplanner.dao.json.interfaces.SourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
