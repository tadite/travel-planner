package edu.nc.travelplanner.repository;

import edu.nc.travelplanner.table.Flight;
import edu.nc.travelplanner.table.TwoWayFlight;
import org.springframework.data.repository.CrudRepository;

public interface TwoWayFlightRepository extends CrudRepository<TwoWayFlight, Long> {
}
