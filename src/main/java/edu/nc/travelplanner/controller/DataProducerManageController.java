package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.dao.json.DataProducerApiDto;
import edu.nc.travelplanner.dao.json.interfaces.DataProducerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manage/producer")
public class DataProducerManageController {

    private DataProducerDao dataProducerDao;

    @Autowired
    public DataProducerManageController(DataProducerDao dataProducerDao) {
        this.dataProducerDao = dataProducerDao;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public DataProducerApiDto save(@RequestBody DataProducerApiDto dataProducerApiDto){
        dataProducerApiDto.getParameterMappers().forEach(mapper -> mapper.setDefaultValue(mapper.getDefValue()));
        dataProducerApiDto.getParameterMappers().forEach(mapper -> {
            if (mapper.getFilterDto()==null || mapper.getFilterDto().getType()==null || mapper.getFilterDto().getParameters()==null)
                mapper.setFilterDto(null);
        });
        return dataProducerDao.save(dataProducerApiDto);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseBody
    public DataProducerApiDto get(@PathVariable(value = "name") String name) {
        return dataProducerDao.get(name);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<DataProducerApiDto> getAll() {
        return dataProducerDao.getAll();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    @ResponseBody
    public DataProducerApiDto delete(@PathVariable(value = "name") String name){
        return dataProducerDao.delete(name);
    }

    @RequestMapping(value = "/namesOnly", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getAllNames() {
        return dataProducerDao.getAll().stream()
                .filter(sourceApiDto -> sourceApiDto.getName() != null)
                .map(sourceApiDto -> sourceApiDto.getName()).collect(Collectors.toList());
    }

}
