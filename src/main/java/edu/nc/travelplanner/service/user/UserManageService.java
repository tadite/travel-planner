package edu.nc.travelplanner.service.user;

import edu.nc.travelplanner.dao.ClientDao;
import edu.nc.travelplanner.table.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class UserManageService {

    private static final int PAGE_SIZE=10;

    @Autowired
    ClientDao clientDao;

    public List<Client> getClientsByPage(int page){
        return clientDao.getAllClients().stream().skip(PAGE_SIZE*(page-1)).limit(PAGE_SIZE*(page)).collect(Collectors.toList());
    }

    public List<Client> getAllClients(){
        return clientDao.getAllClients();
    }

    public Client getClientById(long clientId){
        return clientDao.getClientById(clientId);
    }

    public boolean blockClientById(long id){
        Client client = clientDao.getClientById(id);

        if (client==null)
            return false;
        client.setIsBlocked(!client.getIsBlocked());
        clientDao.saveClient(client);

        return true;
    }
}
