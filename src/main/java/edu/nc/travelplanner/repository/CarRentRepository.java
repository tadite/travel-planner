package edu.nc.travelplanner.repository;

import edu.nc.travelplanner.table.CarRent;
import edu.nc.travelplanner.table.Flight;
import org.springframework.data.repository.CrudRepository;

public interface CarRentRepository  extends CrudRepository<CarRent, Long> {
}
