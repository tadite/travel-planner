package edu.nc.travelplanner.repository;

import edu.nc.travelplanner.table.Flight;
import edu.nc.travelplanner.table.Travel;
import org.springframework.data.repository.CrudRepository;

public interface TravelRepository extends CrudRepository<Travel, Long> {
}
