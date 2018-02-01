package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.OptionDao;
import edu.nc.travelplanner.table.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/option")
public class OptionController {
    @Autowired
    private OptionDao optionDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            Option option = new Option();
            option.setOptionId(id);
            optionDao.delete(option);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Option succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String create(String name) {
        try {
            Option option = new Option();
            option.setName(name);
            optionDao.saveOption(option);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Option succesfully saved!";
    }
    @RequestMapping(value = "/allOptions")
    @ResponseBody
    public List<Option> getAllOptions() {
        try {
            return optionDao.getAllOptions();
        } catch (Exception ex) {
            return null;
        }
    }
}