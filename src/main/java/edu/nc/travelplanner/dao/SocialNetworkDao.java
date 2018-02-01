package edu.nc.travelplanner.dao;


import edu.nc.travelplanner.table.SocialNetwork;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class SocialNetworkDao {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveSocialNetwork (SocialNetwork socialNetwork) {
        Long isSuccess = (Long)getSession().save(socialNetwork);
        if(isSuccess >= 1){
            return "Success";
        }else{
            return "Error while Saving SocialNetwork";
        }

    }

    @Transactional
    public boolean delete(SocialNetwork socialNetwork) {
        getSession().delete(socialNetwork);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<SocialNetwork> getAllSocialNetworks() {
        return getSession().createQuery("from SocialNetwork").list();
    }
}
