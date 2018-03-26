package edu.nc.travelplanner.repository;

import edu.nc.travelplanner.table.City;
import edu.nc.travelplanner.table.Flight;
import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<Flight, Long> {
}

