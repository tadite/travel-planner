package edu.nc.travelplanner.dao;

import edu.nc.travelplanner.table.TypeOfMovement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public class TypeOfMovementDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveTypeOfMovement (TypeOfMovement typeOfMovement) {
        Long isSuccess = (Long)getSession().save(typeOfMovement);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving TypeOfMovement";
        }

    }

    @Transactional
    public boolean delete(TypeOfMovement typeOfMovement) {
        getSession().delete(typeOfMovement);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<TypeOfMovement> getAllTypeOfMovements() {
        return getSession().createQuery("from TypeOfMovement").list();
    }

    @Transactional
    public TypeOfMovement getTypeOfMovementById (Long id) {

        List<TypeOfMovement> list = getSession().createQuery("FROM TypeOfMovement c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }
}