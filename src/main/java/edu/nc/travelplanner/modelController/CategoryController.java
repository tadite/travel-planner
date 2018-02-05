package edu.nc.travelplanner.modelController;


import java.util.List;
import java.util.Set;

import edu.nc.travelplanner.dao.CategoryDao;
import edu.nc.travelplanner.dao.TypeOfMovementDao;
import edu.nc.travelplanner.table.Category;
import edu.nc.travelplanner.table.TypeOfMovement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/category")
@Transactional
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private TypeOfMovementDao typeOfMovementDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public String delete(long id) {
        try {
            Category category = categoryDao.getCategoryById(id);
            if (category.getTypeOfMovements().size() > 0)
                for(TypeOfMovement t: category.getTypeOfMovements())
                    typeOfMovementDao.delete(t);
            categoryDao.delete(category);

        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Category succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String create(String name) {
        try {
            Category category = new Category();
            category.setName(name);
            categoryDao.saveCategory(category);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Category succesfully saved!";
    }
    @RequestMapping(value = "/allCategories")
    @ResponseBody
    @Transactional
    public String getAllCategories() {
        try {
            String result = "[";
            List<Category> categories = categoryDao.getAllCategories();
            for(Category c:categories){

                result +=  "{ \"category_id:\" " + c.getCategoryId()
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