package edu.nc.travelplanner.dao;

import edu.nc.travelplanner.table.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CountryDao {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveCountry (Country country) {
        Long isSuccess = (Long)getSession().save(country);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving Country";
        }

    }

    @Transactional
    public boolean delete(Country country) {
        getSession().delete(country);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Country> getAllCountries() {
        return getSession().createQuery("from Country").list();
    }

    @Transactional
    public Country getCountryByName (String name) {

        List<Country> list = getSession().createQuery("FROM Country c WHERE c.name= :countryName").setParameter("countryName",name).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }

    @Transactional
    public Country getCountryById (Long id) {

        List<Country> list = getSession().createQuery("FROM Country c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }
}
