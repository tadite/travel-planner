package edu.nc.travelplanner.modelController;


import java.util.List;

import edu.nc.travelplanner.dao.ClientDao;
import edu.nc.travelplanner.dao.TravelDao;
import edu.nc.travelplanner.dao.TravelForClientDao;
import edu.nc.travelplanner.table.Client;
import edu.nc.travelplanner.table.Travel;
import edu.nc.travelplanner.table.TravelForClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/travel_for_client")
public class TravelForClientController {
    @Autowired
    private TravelForClientDao travelForClientDao;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private TravelDao travelDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            TravelForClient travelForClient = new TravelForClient();
            travelForClient.setTravelForClientId(id);
            travelForClientDao.delete(travelForClient);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "TravelForClient succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String create(Long client_id,Long travel_id,String description) {
        try {
            TravelForClient travelForClient = new TravelForClient();
            travelForClient.setDescription(description);

            Client client = clientDao.getClientById(client_id);
            if (client != null) {
                travelForClient.setClient(client);
            }


            Travel travel = travelDao.getTravelById(travel_id);
            if (travel != null) {
                travelForClient.setTravel(travel);
            }

            travelForClientDao.saveTravelForClient(travelForClient);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "TravelForClient succesfully saved!";
    }
    @RequestMapping(value = "/allTravelForClients")
    @ResponseBody
    @Transactional
    public String getAllTravelForClients() {
        try {
            String result = "[";
            List<TravelForClient>  travelForClients = travelForClientDao.getAllTravelForClients();
            for(TravelForClient t:travelForClients){

                result += "{ \"travel_for_client_id:\" " + t.getTravelForClientId()
                        +", \"client_id\": " + t.getClient().getClientId()
                        +", \"travel_id\": " + t.getTravel().getTravelId()
                        + ", \"description\":" + t.getDescription() +"},";
            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }

}