package edu.nc.travelplanner.dao;

import edu.nc.travelplanner.table.TypeOfRest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TypeOfRestDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveTypeOfRest (TypeOfRest typeOfRest) {
        Long isSuccess = (Long)getSession().save(typeOfRest);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving TypeOfRest";
        }

    }

    @Transactional
    public boolean delete(TypeOfRest typeOfRest) {
        getSession().delete(typeOfRest);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<TypeOfRest> getAllTypeOfRests() {
        return getSession().createQuery("from TypeOfRest").list();
    }

    @Transactional
    public TypeOfRest getTypeOfRestById (Long id) {

        List<TypeOfRest> list = getSession().createQuery("FROM TypeOfRest c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }
}