package edu.nc.travelplanner.dao;

import edu.nc.travelplanner.table.Option;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OptionDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveOption (Option option) {
        Long isSuccess = (Long)getSession().save(option);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving Option";
        }

    }

    @Transactional
    public boolean delete(Option option) {
        getSession().delete(option);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Option> getAllOptions() {
        return getSession().createQuery("from Option").list();
    }

    @Transactional
    public Option getOptionById (Long id) {

        List<Option> list = getSession().createQuery("FROM Option c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }
}
