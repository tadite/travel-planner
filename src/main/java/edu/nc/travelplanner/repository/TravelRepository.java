package edu.nc.travelplanner.repository;

import edu.nc.travelplanner.table.Flight;
import edu.nc.travelplanner.table.Travel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TravelRepository extends PagingAndSortingRepository<Travel, Long> {
    List<Travel> findAllByClient_ClientId(Long clientId);
    Page<Travel> findAllByClient_ClientId(Pageable pageable, Long clientId);
}
