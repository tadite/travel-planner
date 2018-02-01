package edu.nc.travelplanner.dao;

import edu.nc.travelplanner.table.CheckPoint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CheckPointDao {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveCheckPoint (CheckPoint checkPoint) {
        Long isSuccess = (Long)getSession().save(checkPoint);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving CheckPoint";
        }

    }

    @Transactional
    public boolean delete(CheckPoint checkPoint) {
        getSession().delete(checkPoint);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<CheckPoint> getAllCheckPoints() {
        return getSession().createQuery("from CheckPoint").list();
    }

    @Transactional
    public CheckPoint getCheckPointById (Long id) {

        List<CheckPoint> list = getSession().createQuery("FROM CheckPoint c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }
}
