package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.PlaceOfResidenceDao;
import edu.nc.travelplanner.dao.TypeOfResidenceDao;
import edu.nc.travelplanner.table.PlaceOfResidence;
import edu.nc.travelplanner.table.TypeOfResidence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/type_of_residence")
public class TypeOfResidenceController {

    @Autowired
    private TypeOfResidenceDao typeOfResidenceDao;

    @Autowired
    private PlaceOfResidenceDao placeOfResidenceDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public String delete(long id) {
        try {
            TypeOfResidence typeOfResidence = typeOfResidenceDao.getTypeOfResidenceById(id);
            if (typeOfResidence.getPlaceOfResidences().size() > 0)
                for(PlaceOfResidence p: typeOfResidence.getPlaceOfResidences())
                    placeOfResidenceDao.delete(p);
            typeOfResidenceDao.delete(typeOfResidence);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "TypeOfResidence succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String create(String name, String description) {
        try {
            TypeOfResidence typeOfResidence = new TypeOfResidence();

            typeOfResidence.setName(name);
            typeOfResidence.setDescription(description);

            typeOfResidenceDao.saveTypeOfResidence(typeOfResidence);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "TypeOfResidence succesfully saved!";
    }

    @RequestMapping(value = "/allTypeOfResidences")
    @ResponseBody
    @Transactional
    public String getAllTypeOfResidences() {
        try {
            String result = "[";
            List<TypeOfResidence> typeOfResidences = typeOfResidenceDao.getAllTypeOfResidences();
            for(TypeOfResidence t:typeOfResidences ){

                result += "{ \"type_of_residences _id:\" " + t.getTypeOfResidenceId()
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