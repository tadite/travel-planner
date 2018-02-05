package edu.nc.travelplanner.dao;

import edu.nc.travelplanner.table.Excursion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ExcursionDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveExcursion (Excursion excursion) {
        Long isSuccess = (Long)getSession().save(excursion);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving Excursion";
        }

    }

    @Transactional
    public boolean delete(Excursion excursion) {
        getSession().delete(excursion);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Excursion> getAllExcursions() {
        return getSession().createQuery("from Excursion").list();
    }

    @Transactional
    public Excursion getExcursionById (Long id) {

        List<Excursion> list = getSession().createQuery("FROM Excursion c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }
}

