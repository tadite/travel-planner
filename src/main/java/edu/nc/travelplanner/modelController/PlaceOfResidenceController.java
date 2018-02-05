package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.CheckPointDao;
import edu.nc.travelplanner.dao.CityDao;
import edu.nc.travelplanner.dao.PlaceOfResidenceDao;
import edu.nc.travelplanner.dao.TypeOfResidenceDao;
import edu.nc.travelplanner.table.CheckPoint;
import edu.nc.travelplanner.table.City;
import edu.nc.travelplanner.table.PlaceOfResidence;
import edu.nc.travelplanner.table.TypeOfResidence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/place_of_residence")
public class PlaceOfResidenceController {

    @Autowired
    private PlaceOfResidenceDao placeOfResidenceDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private TypeOfResidenceDao typeOfResidenceDao;

    @Autowired
    private CheckPointDao checkPointDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public String delete(long id) {
        try {
            PlaceOfResidence placeOfResidence = placeOfResidenceDao.getPlaceOfResidenceById(id);
            if (placeOfResidence.getCheckPoints().size() > 0)
                for(CheckPoint c: placeOfResidence.getCheckPoints())
                    checkPointDao.delete(c);
            placeOfResidenceDao.delete(placeOfResidence);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "PlaceOfResidence succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional
    public String create(Long city_id, Long typeOfResidence_id, String name, String climate, String description, String costPerDay) {
        try {
            PlaceOfResidence placeOfResidence = new PlaceOfResidence();

            placeOfResidence.setName(name);
            placeOfResidence.setClimate(climate);
            placeOfResidence.setDescription(description);
            placeOfResidence.setCostPerDay(costPerDay);

            City city = cityDao.getCityById(city_id);
            if (city != null) {
                placeOfResidence.setCity(city);
                placeOfResidence.setCountry(city.getCountry());
            }

            TypeOfResidence typeOfResidence = typeOfResidenceDao.getTypeOfResidenceById(typeOfResidence_id);
            if (typeOfResidence != null) {
                placeOfResidence.setTypeOfResidence(typeOfResidence);
            }

            placeOfResidenceDao.savePlaceOfResidence(placeOfResidence);

            city.addPlaceOfResidence(placeOfResidence);
            placeOfResidence.getCountry().addPlaceOfResidence(placeOfResidence);
            typeOfResidence.addPlaceOfResidence(placeOfResidence);

        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "PlaceOfResidence succesfully saved!";
    }

    @RequestMapping(value = "/allPlaceOfResidences")
    @ResponseBody
    @Transactional
    public String getAllPlaceOfResidences() {
        try {
            String result = "[";
            List<PlaceOfResidence> placeOfResidences = placeOfResidenceDao.getAllPlaceOfResidences();
            for(PlaceOfResidence p:placeOfResidences){

                result += "{ \"place_of_residences_id:\" " + p.getPlaceOfResidenceId()
                        +", \"city_id\": " + p.getCity().getCityId()
                        + ", \"country_id\": " + p.getCity().getCountry().getCountryId()
                        + ", \"typeOfResidence_id\": " + p.getTypeOfResidence().getTypeOfResidenceId()
                        + ", \"name\": " + p.getName()
                        + ", \"climate\": " + p.getClimate()
                        + ", \"description\": " + p.getDescription()
                        + ", \"costPerDay\":" + p.getCostPerDay() +"}";
            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }

}