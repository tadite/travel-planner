package edu.nc.travelplanner.repository;

import edu.nc.travelplanner.table.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByLogin(String login);
}
