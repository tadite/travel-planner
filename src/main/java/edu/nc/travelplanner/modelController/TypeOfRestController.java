package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.CheckPointDao;
import edu.nc.travelplanner.dao.TypeOfRestDao;
import edu.nc.travelplanner.table.CheckPoint;
import edu.nc.travelplanner.table.TypeOfRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/type_of_rest")
public class TypeOfRestController {

    @Autowired
    private TypeOfRestDao typeOfRestDao;

    @Autowired
    private CheckPointDao checkPointDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public String delete(long id) {
        try {
            TypeOfRest typeOfRest = typeOfRestDao.getTypeOfRestById(id);
            if (typeOfRest.getCheckPoints().size() > 0)
                for(CheckPoint c: typeOfRest.getCheckPoints())
                    checkPointDao.delete(c);
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
    @Transactional
    public String getAllTypeOfRests() {
        try {
            String result = "[";
            List<TypeOfRest> typeOfRests = typeOfRestDao.getAllTypeOfRests();
            for(TypeOfRest t:typeOfRests  ){

                result += "{ \"type_of_rest _id:\" " + t.getTypeOfRestId()
                        + ", \"name\": " + t.getName()
                        + ", \"description\": " + t.getDescription() + "}";
            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }
}