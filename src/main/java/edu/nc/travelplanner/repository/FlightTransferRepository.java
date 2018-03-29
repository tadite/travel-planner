package edu.nc.travelplanner.repository;

import edu.nc.travelplanner.table.FlightTransfer;
import edu.nc.travelplanner.table.Travel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FlightTransferRepository extends CrudRepository<FlightTransfer, Long> {
}
