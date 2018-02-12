package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.dao.json.SourceApiDto;
import edu.nc.travelplanner.dao.json.interfaces.SourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController()
@RequestMapping("/api/manage/source")
public class SourceManageController {

    private SourceDao sourceDao;

    @Autowired
    public SourceManageController(SourceDao sourceDao) {
        this.sourceDao = sourceDao;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public SourceApiDto save(@RequestBody SourceApiDto sourceApiDto){
        return sourceDao.save(sourceApiDto);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    public SourceApiDto get(@PathVariable(value = "name") String name) {
        return sourceDao.get(name);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<SourceApiDto> getAll() {
        return sourceDao.getAll();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    @ResponseBody
    public SourceApiDto delete(@PathVariable(value = "name") String name){
        return sourceDao.delete(name);
    }

}
