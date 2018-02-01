package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.TravelDao;
import edu.nc.travelplanner.table.Travel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value = "/travel")
public class TravelController {
    @Autowired
    private TravelDao travelDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            Travel travel = new Travel();
            travel.setTravelId(id);
            travelDao.delete(travel);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Travel succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String create(String name, Integer numberOfDays, String description,String cost) {
        try {
            Travel travel = new Travel();

            travel.setName(name);
            travel.setNumberOfDays(numberOfDays);
            travel.setDescription(description);
            travel.setCost(cost);

            travelDao.saveTravel(travel);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Travel succesfully saved!";
    }
    @RequestMapping(value = "/allTravels")
    @ResponseBody
    public List<Travel> getAllTravels() {
        try {
            return travelDao.getAllTravels();
        } catch (Exception ex) {
            return null;
        }
    }
}