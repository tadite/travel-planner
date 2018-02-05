package edu.nc.travelplanner.dao;


import edu.nc.travelplanner.table.OptionForTravel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OptionForTravelDao {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveOptionForTravel (OptionForTravel optionForTravel) {
        Long isSuccess = (Long)getSession().save(optionForTravel);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving OptionForTravel";
        }

    }

    @Transactional
    public boolean delete(OptionForTravel optionForTravel) {
        getSession().delete(optionForTravel);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<OptionForTravel> getAllOptionForTravels() {
        return getSession().createQuery("from OptionForTravel").list();
    }

    @Transactional
    public OptionForTravel getOptionForTravelById (Long id) {

        List<OptionForTravel> list = getSession().createQuery("FROM OptionForTravel c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }
}
