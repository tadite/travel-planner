package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.TypeOfRestDao;
import edu.nc.travelplanner.table.TypeOfRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/type_of_rest")
public class TypeOfRestController {
    @Autowired
    private TypeOfRestDao typeOfRestDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            TypeOfRest typeOfRest = new TypeOfRest();
            typeOfRest.setTypeOfRestId(id);
            typeOfRestDao.delete(typeOfRest);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "TypeOfRest succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String create(String name, String description) {
        try {
            TypeOfRest typeOfRest = new TypeOfRest();

            typeOfRest.setName(name);
            typeOfRest.setDescription(description);

            typeOfRestDao.saveTypeOfRest(typeOfRest);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "TypeOfRest succesfully saved!";
    }
    @RequestMapping(value = "/allTypeOfRests")
    @ResponseBody
    public List<TypeOfRest> getAllTypeOfRests() {
        try {
            return typeOfRestDao.getAllTypeOfRests();
        } catch (Exception ex) {
            return null;
        }
    }
}