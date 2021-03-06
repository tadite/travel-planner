package edu.nc.travelplanner.dao;

import edu.nc.travelplanner.table.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ClientDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public String saveClient(Client client) {
        getSession().saveOrUpdate(client);
        if(2 >= 1){
            return "Success";
        }else{
            return "Error while Saving Client";
        }

    }

    @Transactional
    public boolean delete(Client client) {
        getSession().delete(client);
        return true;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Client> getAllClients() {
        return getSession().createQuery("from Client").list();
    }

    public Client getClientById (Long id) {

        List<Client> list = getSession().createQuery("FROM Client c WHERE c.id= :Id").setParameter("Id",id).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }

    public Client getClientByLogin (String login) {
        List<Client> list = getSession().createQuery("FROM Client c WHERE c.login= :Login").setParameter("Login",login).list();
        if(!list.isEmpty()) {
            return  list.get(0);
        }
        return null;
    }


}
