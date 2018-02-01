package edu.nc.travelplanner.dao;

import edu.nc.travelplanner.table.PlaceOfResidence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PlaceOfResidenceDao {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String savePlaceOfResidence (PlaceOfResidence placeOfResidence) {
        Long isSuccess = (Long)getSession().save(placeOfResidence);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving PlaceOfResidence";
        }

    }

    @Transactional
    public boolean delete(PlaceOfResidence placeOfResidence) {
        getSession().delete(placeOfResidence);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<PlaceOfResidence> getAllPlaceOfResidences() {
        return getSession().createQuery("from PlaceOfResidence").list();
    }

    @Transactional
    public PlaceOfResidence getPlaceOfResidenceById (Long id) {

        List<PlaceOfResidence> list = getSession().createQuery("FROM PlaceOfResidence c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }
}

