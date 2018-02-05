package edu.nc.travelplanner.modelController;

import java.util.List;

import edu.nc.travelplanner.dao.*;
import edu.nc.travelplanner.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/check_point")
public class CheckPointController {

    @Autowired
    private CheckPointDao checkPointDao;

    @Autowired
    private TravelDao travelDao;

    @Autowired
    private TypeOfRestDao typeOfRestDao;

    @Autowired
    private TypeOfMovementDao typeOfMovementDao;

    @Autowired
    private PlaceOfResidenceDao placeOfResidenceDao;

    @Autowired
    private ExcursionDao excursionDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public String delete(long id) {
        try {
            CheckPoint checkPoint = checkPointDao.getCheckPointById(id);
            if (checkPoint.getExcursions().size() > 0)
                for(Excursion e: checkPoint.getExcursions())
                    excursionDao.delete(e);

            checkPointDao.delete(checkPoint);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "CheckPoint succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional
    public String create(Long travel_id, Integer numberOfDays, String cost, String description, Long  typeOfRest_id, Long typeOfMovement_id, Long placeOfResidence_id) {
        try {

            CheckPoint checkPoint = new CheckPoint();

            Travel travel = travelDao.getTravelById(travel_id);
            if (travel != null) {
                checkPoint.setTravel(travel);
            }

            checkPoint.setNumberOfDays(numberOfDays);
            checkPoint.setCost(cost);
            checkPoint.setDescription(description);

            TypeOfRest typeOfRest = typeOfRestDao.getTypeOfRestById(typeOfRest_id);
            if (typeOfRest != null) {
                checkPoint.setTypeOfRest(typeOfRest);
            }

            TypeOfMovement typeOfMovement = typeOfMovementDao.getTypeOfMovementById(typeOfMovement_id);
            if (typeOfMovement != null) {
                checkPoint.setTypeOfMovement(typeOfMovement);
            }

            PlaceOfResidence placeOfResidence = placeOfResidenceDao.getPlaceOfResidenceById(placeOfResidence_id);
            if (placeOfResidence != null) {
                checkPoint.setPlaceOfResidence(placeOfResidence);
            }

            travel.addCheckPoint(checkPoint);
            typeOfRest.addCheckPoint(checkPoint);
            typeOfMovement.addCheckPoint(checkPoint);
            placeOfResidence.addCheckPoint(checkPoint);

            checkPointDao.saveCheckPoint(checkPoint);

        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "CheckPoint succesfully saved!";
    }
    @RequestMapping(value = "/allCheckPoints")
    @ResponseBody
    @Transactional
    public String getAllCheckPoints() {
        try {
            String result = "[";
            List<CheckPoint> checkPoints = checkPointDao.getAllCheckPoints();
            for(CheckPoint cp:checkPoints){

                result +=  "{ \"check_point_id:\" " + cp.getCheckPointId()
                                +  ", \"travel_id\": " + cp.getTravel().getTravelId()
                                +  ", \"numberOfDays\": " + cp.getNumberOfDays()
                                +  ", \"cost\": " + cp.getCost()
                                +  ", \"description\": " + cp.getDescription()
                                +  ", \"typeOfRest_id\": " + cp.getTypeOfRest().getTypeOfRestId()
                                +  ", \"typeOfMovement_id\": " + cp.getTypeOfMovement().getTypeOfMovementId()
                                + ", \"placeOfResidence_id\": " + cp.getPlaceOfResidence().getPlaceOfResidenceId()  + "}";
            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }

}