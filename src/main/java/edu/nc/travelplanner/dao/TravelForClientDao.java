package edu.nc.travelplanner.dao;

import edu.nc.travelplanner.table.TravelForClient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public class TravelForClientDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveTravelForClient (TravelForClient travelForClient) {
        Long isSuccess = (Long)getSession().save(travelForClient);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving TravelForClient";
        }

    }

    @Transactional
    public boolean delete(TravelForClient travelForClient) {
        getSession().delete(travelForClient);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<TravelForClient> getAllTravelForClients() {
        return getSession().createQuery("from TravelForClient").list();
    }

    @Transactional
    public TravelForClient getTravelForClientById (Long id) {

        List<TravelForClient> list = getSession().createQuery("FROM TravelForClient c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }
}
