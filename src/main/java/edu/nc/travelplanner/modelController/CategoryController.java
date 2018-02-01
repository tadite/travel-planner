package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.CategoryDao;
import edu.nc.travelplanner.table.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            Category category = new Category();
            category.setCategoryId(id);
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
    public List<Category> getAllCategories() {
        try {
            return categoryDao.getAllCategories();
        } catch (Exception ex) {
            return null;
        }
    }
}