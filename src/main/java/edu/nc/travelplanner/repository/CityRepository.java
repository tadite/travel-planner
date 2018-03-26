package edu.nc.travelplanner.repository;

import com.google.common.base.Optional;
import edu.nc.travelplanner.table.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
    Optional<City> findOptionalByName(String name);
}
