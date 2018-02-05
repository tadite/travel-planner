package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.OptionDao;
import edu.nc.travelplanner.dao.OptionForTravelDao;
import edu.nc.travelplanner.table.Option;
import edu.nc.travelplanner.table.OptionForTravel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/option")
public class OptionController {
    @Autowired
    private OptionDao optionDao;

    @Autowired
    private OptionForTravelDao optionForTravelDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public String delete(long id) {
        try {
            Option option = optionDao.getOptionById(id);
            if (option.getOptionForTravels().size() > 0)
                for(OptionForTravel o: option.getOptionForTravels())
                    optionForTravelDao.delete(o);

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
    @Transactional
    public String getAllOptions() {
        try {
            String result = "[";
            List<Option> options = optionDao.getAllOptions();
            for(Option o:options){

                result += "{ \"option_id:\" " + o.getOptionId()
                        + ", \"name\": " + o.getName() +"}";
            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }
}