package edu.nc.travelplanner.dao;

import edu.nc.travelplanner.table.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CityDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveCity (City city) {
        Long isSuccess = (Long)getSession().save(city);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving City";
        }

    }

    @Transactional
    public boolean delete(City city) {
        getSession().delete(city);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<City> getAllCities() {
        return getSession().createQuery("from City").list();
    }

    @Transactional
    public City getCityById (Long id) {

        List<City> list = getSession().createQuery("FROM City c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }

    @Transactional
    public Long getId (City city) {

        List<City> list = getSession().createQuery("from City").list();
        if(!list.isEmpty()) {
            for(City c:list)
                if (c.equals(city))
                    return c.getCityId();
        }
        return null;
    }
}

