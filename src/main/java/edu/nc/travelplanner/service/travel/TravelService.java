package edu.nc.travelplanner.service.travel;

import edu.nc.travelplanner.dao.ClientDao;
import edu.nc.travelplanner.dao.TravelDao;
import edu.nc.travelplanner.table.Client;
import edu.nc.travelplanner.table.Travel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TravelService {

    private static final int PAGE_SIZE=10;

    @Autowired
    TravelDao travelDao;

    public List<Travel> getTravelsByPage(Integer page){
        return travelDao.getAllTravels().stream().skip(PAGE_SIZE*(page-1)).limit(PAGE_SIZE*(page)).collect(Collectors.toList());
    }

    public List<Travel> getAllTravels(){
        return travelDao.getAllTravels();
    }

    public Travel getTravelById(Long travelId){
        return travelDao.getTravelById(travelId);
    }

    public Travel deleteTravelById(Long travelId){
        return travelDao.deleteById(travelId);
    }
}
