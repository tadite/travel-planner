package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.dao.json.JumpGraphDto;
import edu.nc.travelplanner.dao.json.JumpInsertDto;
import edu.nc.travelplanner.dao.json.SourceApiDto;
import edu.nc.travelplanner.dao.json.interfaces.JumpDao;
import edu.nc.travelplanner.model.factory.tree.JumpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/manage/jump")
public class JumpManageController {

    @Autowired
    private JumpDao jumpDao;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<JumpDto> getAll() {
        return jumpDao.getAll();
    }

    @RequestMapping(value = "/{from}/{to}", method = RequestMethod.GET)
    @ResponseBody
    public JumpDto get(@PathVariable(value = "from") String from, @PathVariable(value = "to") String to) {
        return jumpDao.get(from, to);
    }

    @RequestMapping(value = "/{from}/{to}", method = RequestMethod.DELETE)
    @ResponseBody
    public JumpDto delete(@PathVariable(value = "from") String from, @PathVariable(value = "to") String to) {
        return jumpDao.delete(from, to);
    }

    @RequestMapping(value = "/{from}/{to}", method = RequestMethod.POST)
    @ResponseBody
    public JumpDto update(@PathVariable(value = "from") String from, @PathVariable(value = "to") String to, @RequestBody JumpDto jumpDto) {
        return jumpDao.update(from, to, jumpDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public JumpDto save(@RequestBody JumpDto jumpDto) {
        return jumpDao.save(jumpDto);
    }


    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public JumpDto save(@RequestBody JumpInsertDto jumpInsertDto) {
        return jumpDao.insert(jumpInsertDto);
    }


}
