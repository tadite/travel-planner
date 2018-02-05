package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.OptionDao;
import edu.nc.travelplanner.dao.OptionForTravelDao;
import edu.nc.travelplanner.dao.TravelDao;
import edu.nc.travelplanner.table.Option;
import edu.nc.travelplanner.table.OptionForTravel;
import edu.nc.travelplanner.table.Travel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/option_for_travel")
public class OptionForTravelController{
    @Autowired
    private OptionForTravelDao optionForTravelDao;

    @Autowired
    private TravelDao travelDao;

    @Autowired
    private OptionDao optionDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public String delete(long id) {
        try {
            OptionForTravel optionForTravel  = optionForTravelDao.getOptionForTravelById(id);
            optionForTravelDao.delete(optionForTravel);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "OptionForTravel succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional
    public String create(Long travel_id, Long option_id, String description) {
        try {
            OptionForTravel optionForTravel = new OptionForTravel();

            optionForTravel.setDescription(description);

            Travel travel = travelDao.getTravelById(travel_id);
            if (travel != null) {
                optionForTravel.setTravel(travel);
            }

            Option option = optionDao.getOptionById(option_id);
            if (option != null) {
                optionForTravel.setOption(option);
            }

            optionForTravelDao.saveOptionForTravel(optionForTravel);
            travel.addOptionForTravel(optionForTravel);
            option.addOptionForTravel(optionForTravel);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "OptionForTravel succesfully saved!";
    }

    @RequestMapping(value = "/allOptionForTravels")
    @ResponseBody
    @Transactional
    public String getAllOptionForTravels() {
        try {
            String result = "[";
            List<OptionForTravel>  optionForTravels = optionForTravelDao.getAllOptionForTravels();
            for(OptionForTravel o:optionForTravels){

                result += "{ \"option_for_travel_id:\" " + o.getOptionForTravelId()
                        +", \"travel_id\": " + o.getTravel().getTravelId()
                        + ", \"option_id\": " + o.getOption().getOptionId()
                        + ", \"description\":" + o.getDescription() +"}";
            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }
}