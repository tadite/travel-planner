package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.CountryDao;
import edu.nc.travelplanner.table.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/country")
public class CountryController {
    @Autowired
    private CountryDao countryDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            Country country = new Country();
            country.setCountryId(id);
            countryDao.delete(country);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Country succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String create(String name) {
        try {
            Country country= new Country();
            country.setName(name);

            countryDao.saveCountry(country);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Country succesfully saved!";
    }
    @RequestMapping(value = "/allCountries")
    @ResponseBody
    public List<Country> getAllcountries() {
        try {
            return countryDao.getAllCountries();
        } catch (Exception ex) {
            return null;
        }
    }
}