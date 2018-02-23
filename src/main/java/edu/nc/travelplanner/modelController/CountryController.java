package edu.nc.travelplanner.modelController;


import java.util.List;
import java.util.Set;

import edu.nc.travelplanner.dao.*;
import edu.nc.travelplanner.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/country")
public class CountryController {

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private ExcursionDao excursionDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private PlaceOfResidenceDao placeOfResidenceDao;

    @Autowired
    private ClientDao clientDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public String delete(Integer id) {
        try {
            Country country = countryDao.getCountryById(id);
            if (country.getExcursions().size() > 0)
                for(Excursion e: country.getExcursions())
                    excursionDao.delete(e);


            if (country.getCities().size() > 0)
                for(City c: country.getCities())
                    cityDao.delete(c);

            if (country.getPlaceOfResidences().size() > 0)
                for(PlaceOfResidence p: country.getPlaceOfResidences())
                    placeOfResidenceDao.delete(p);

            if (country.getClients().size() > 0)
                for(Client c: country.getClients())
                    clientDao.delete(c);

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
    @Transactional
    public String getAllcountries() {
        try {
            String result = "[";
            List<Country> countries = countryDao.getAllCountries();
            for(Country c:countries){

                result +=  "{ \"country_id:\" " + c.getCountryId()
                        + ", \"name\": " + c.getName()  + "}";
            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }
}