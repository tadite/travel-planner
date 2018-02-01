package edu.nc.travelplanner.modelController;

import java.util.List;

import edu.nc.travelplanner.dao.CityDao;
import edu.nc.travelplanner.dao.CountryDao;
import edu.nc.travelplanner.table.City;
import edu.nc.travelplanner.table.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/city")
public class CityController {
    @Autowired
    private CityDao cityDao;

    @Autowired
    private CountryDao countryDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            City city = new City();
            city.setCityId(id);
            cityDao.delete(city);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "City succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String create(String name, Long country_id) {
        try {

            City city = new City();
            city.setName(name);

            Country country = countryDao.getCountryById(country_id);
            if (country  != null) {
                city.setCountry(country);
            }

            cityDao.saveCity(city);

        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "City succesfully saved!";
    }


    @RequestMapping(value = "/allCities")
    @ResponseBody
    @Transactional
    public String getAllCities() {
        try {
            String result = "[";
            List<City> cities = cityDao.getAllCities();
            for(City c:cities){

                result +=  "{ \"city_id:\" " + c.getCityId()
                        +  ", \"name\": " + c.getName()
                        + ", \"country_id\": " + c.getCountry().getCountryId()  + "},";
            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }
}