package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.CheckPointDao;
import edu.nc.travelplanner.dao.OptionForTravelDao;
import edu.nc.travelplanner.dao.TravelDao;
import edu.nc.travelplanner.dao.TravelForClientDao;
import edu.nc.travelplanner.table.CheckPoint;
import edu.nc.travelplanner.table.OptionForTravel;
import edu.nc.travelplanner.table.Travel;
import edu.nc.travelplanner.table.TravelForClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value = "/travel")
public class TravelController {
    @Autowired
    private TravelDao travelDao;

    @Autowired
    private CheckPointDao checkPointDao;

    @Autowired
    private TravelForClientDao travelForClientDao;

    @Autowired
    private OptionForTravelDao optionForTravelDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public String delete(long id) {
        try {
            Travel travel = travelDao.getTravelById(id);
            if (travel.getCheckPoints().size() > 0)
                for(CheckPoint c: travel.getCheckPoints())
                    checkPointDao.delete(c);

            if (travel.getTravelForClients().size() > 0)
                for(TravelForClient t: travel.getTravelForClients())
                    travelForClientDao.delete(t);

            if (travel.getOptionForTravels().size() > 0)
                for(OptionForTravel o: travel.getOptionForTravels())
                    optionForTravelDao.delete(o);

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
    @Transactional
    public String getAllTravels() {
        try {
            String result = "[";
            List<Travel> travels = travelDao.getAllTravels();
            for(Travel t:travels){

                result += "{ \"travel_id:\" " + t.getTravelId()
                        + ", \"name\": " + t.getName()
                        + ", \"numberOfDays\": " + t.getNumberOfDays()
                        + ", \"description\": " + t.getDescription()
                        +", \"cost\": " + t.getCost() +"}";
            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }
}