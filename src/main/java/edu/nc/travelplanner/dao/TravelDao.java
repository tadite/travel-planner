package edu.nc.travelplanner.dao;


import edu.nc.travelplanner.table.Travel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TravelDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveTravel (Travel travel) {
        Long isSuccess = (Long)getSession().save(travel);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving Travel";
        }

    }

    @Transactional
    public boolean delete(Travel travel) {
        getSession().delete(travel);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Travel> getAllTravels() {
        return getSession().createQuery("from Travel").list();
    }

    @Transactional
    public Travel getTravelById (Long id) {

        List<Travel> list = getSession().createQuery("FROM Travel c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }
}
