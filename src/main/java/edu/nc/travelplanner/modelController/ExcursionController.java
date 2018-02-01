package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.CheckPointDao;
import edu.nc.travelplanner.dao.CityDao;
import edu.nc.travelplanner.dao.ExcursionDao;
import edu.nc.travelplanner.table.CheckPoint;
import edu.nc.travelplanner.table.City;
import edu.nc.travelplanner.table.Country;
import edu.nc.travelplanner.table.Excursion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/excursion")
public class ExcursionController {
    @Autowired
    private ExcursionDao excursionDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private CheckPointDao checkPointDao;


    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            Excursion excursion = new Excursion();
            excursion.setExcursionId(id);
            excursionDao.delete(excursion);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Excursion succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String create(String name, Long city_id, String duration, Long checkPoint_id, String cost) {
        try {
            Excursion excursion = new Excursion();

            excursion.setName(name);
            excursion.setDuration(duration);
            excursion.setCost(cost);

            City city = cityDao.getCityById(city_id);
            if (city != null) {
                excursion.setCity(city);
                excursion.setCountry(city.getCountry());
            }

            CheckPoint checkPoint = checkPointDao.getCheckPointById(checkPoint_id);
            if (checkPoint != null) {
                excursion.setCheckPoint(checkPoint);
            }

            excursionDao.saveExcursion(excursion);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Excursion succesfully saved!";
    }
/*
    @RequestMapping(value = "/allExcursions")
    @ResponseBody
    @Transactional
    public List<Excursion> getAllExcursions() {
        try {

            return excursionDao.getAllExcursions();
        } catch (Exception ex) {
            return null;
        }
    }*/

    @RequestMapping(value = "/allExcursions")
    @ResponseBody
    @Transactional
    public String getAllExcursions() {
        try {
            String result = "[";
            List<Excursion> excursions = excursionDao.getAllExcursions();
            for(Excursion ex:excursions){

                result += "{ \"excursion_id:\" " + ex.getExcursionId()
                            + ", \"name\": " + ex.getName()
                            +", \"city_id\": " + ex.getCity().getCityId()
                            + ", \"country_id\": " + ex.getCity().getCountry().getCountryId()
                            + ", \"duration\": " + ex.getDuration()
                            + ", \"check_point_id\": " + ex.getCheckPoint().getCheckPointId()
                            + ", \"cost\":" + ex.getCost() +"},";
            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }
}