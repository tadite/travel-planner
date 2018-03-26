package edu.nc.travelplanner.repository;

import com.google.common.base.Optional;
import edu.nc.travelplanner.table.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {
    Optional<Country> findOptionalByName(String name);
}
