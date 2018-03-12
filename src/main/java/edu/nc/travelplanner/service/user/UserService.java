package edu.nc.travelplanner.service.user;

import edu.nc.travelplanner.dao.ClientDao;
import edu.nc.travelplanner.table.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ClientDao clientDao;

    public Client save(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        String result = clientDao.saveClient(client);
        if (result.equals("Success"))
            return clientDao.getClientByLogin(client.getLogin());

        return null;
    }

    public Client find(String login) {
        return clientDao.getClientByLogin(login);
    }

    public Client find(Long id) {
        return clientDao.getClientById(id);
    }
}
