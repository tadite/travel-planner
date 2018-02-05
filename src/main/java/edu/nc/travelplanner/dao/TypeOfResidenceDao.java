package edu.nc.travelplanner.dao;

import edu.nc.travelplanner.table.TypeOfResidence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public class TypeOfResidenceDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveTypeOfResidence (TypeOfResidence typeOfResidence) {
        Long isSuccess = (Long)getSession().save(typeOfResidence);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving TypeOfResidence";
        }

    }

    @Transactional
    public boolean delete(TypeOfResidence typeOfResidence) {
        getSession().delete(typeOfResidence);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<TypeOfResidence> getAllTypeOfResidences() {
        return getSession().createQuery("from TypeOfResidence").list();
    }

    @Transactional
    public TypeOfResidence getTypeOfResidenceById (Long id) {

        List<TypeOfResidence> list = getSession().createQuery("FROM TypeOfResidence c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }
}