package edu.nc.travelplanner.dao;

import java.util.List;

import javax.transaction.Transactional;

import edu.nc.travelplanner.table.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveCategory (Category category) {
        Long isSuccess = (Long)getSession().save(category);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving Category";
        }

    }

    @Transactional
    public boolean delete(Category category) {
        getSession().delete(category);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Category> getAllCategories() {
        return getSession().createQuery("from Category").list();
    }

    @Transactional
    public Category getCategoryById (Long id) {

        List<Category> list = getSession().createQuery("FROM Category c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }
}
