package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.CategoryDao;
import edu.nc.travelplanner.dao.CheckPointDao;
import edu.nc.travelplanner.dao.TypeOfMovementDao;
import edu.nc.travelplanner.table.Category;
import edu.nc.travelplanner.table.CheckPoint;
import edu.nc.travelplanner.table.TypeOfMovement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "type_of_movement")
public class TypeOfMovementController  {
    @Autowired
    private TypeOfMovementDao typeOfMovementDao;

    @Autowired
    private CategoryDao  categoryDao;

    @Autowired
    private CheckPointDao checkPointDao;


    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public String delete(long id) {
        try {
            TypeOfMovement typeOfMovement = typeOfMovementDao.getTypeOfMovementById(id);
            if (typeOfMovement.getCheckPoints().size() > 0)
                for(CheckPoint c: typeOfMovement.getCheckPoints())
                    checkPointDao.delete(c);

            typeOfMovementDao.delete(typeOfMovement);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "TypeOfMovement succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional
    public String create(String duration,Integer numberOfPeople,Long category_id,String description,String cost) {
        try {
            TypeOfMovement typeOfMovement = new TypeOfMovement();

            typeOfMovement.setDuration(duration);
            typeOfMovement.setNumberOfPeople(numberOfPeople);
            typeOfMovement.setDescription(description);
            typeOfMovement.setCost(cost);

            Category category = categoryDao.getCategoryById(category_id);
            if (category != null) {
                typeOfMovement.setCategory(category);
            }

            typeOfMovementDao.saveTypeOfMovement(typeOfMovement);
            category.addTypeOfMovement(typeOfMovement);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "TypeOfMovement succesfully saved!";
    }
    @RequestMapping(value = "/allTypeOfMovements")
    @ResponseBody
    @Transactional
    public String getAllTypeOfMovements() {
        try {
            String result = "[";
            List<TypeOfMovement> typeOfMovements = typeOfMovementDao.getAllTypeOfMovements();
            for(TypeOfMovement t:typeOfMovements){

                result += "{ \"type_of_movemen_id:\" " + t.getTypeOfMovementId()
                        + ", \"duration\": " + t.getDuration()
                        + ", \" numberOfPeople\": " + t.getNumberOfPeople()
                        +", \"category_id\": " + t.getCategory().getCategoryId()
                        + ", \"description\": " + t.getDescription()
                        + ", \"cost\":" + t.getCost() +"}";
            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }

}