package edu.nc.travelplanner.repository;

import com.google.common.base.Optional;
import edu.nc.travelplanner.table.Flight;
import edu.nc.travelplanner.table.Hotel;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends CrudRepository<Hotel, Long> {
}
