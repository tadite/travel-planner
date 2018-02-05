package edu.nc.travelplanner.modelController;

import java.util.List;

import edu.nc.travelplanner.dao.ClientDao;
import edu.nc.travelplanner.dao.SocialNetworkDao;
import edu.nc.travelplanner.table.Client;
import edu.nc.travelplanner.table.SocialNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/social_network")
public class SocialNetworkController {
    @Autowired
    private SocialNetworkDao socialNetworkDao;

    @Autowired
    private ClientDao clientDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public String delete(long id) {
        try {
            SocialNetwork socialNetwork = socialNetworkDao.getSocialNetworkById(id);
            socialNetwork.setSocialNetworkId(id);
            socialNetworkDao.delete(socialNetwork);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "SocialNetwork succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional
    public String create(String name,Long client_id) {
        try {
            SocialNetwork socialNetwork = new SocialNetwork();

            socialNetwork.setName(name);

            Client client = clientDao.getClientById(client_id);
            if (client != null) {
                socialNetwork.setClient(client);
            }

            client.addSocialNetwork(socialNetwork);
            socialNetworkDao.saveSocialNetwork(socialNetwork);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "SocialNetwork succesfully saved!";
    }

    @RequestMapping(value = "/allSocialNetworks")
    @ResponseBody
    @Transactional
    public String getAllSocialNetworks() {
        try {
            String result = "[";
            List<SocialNetwork> socialNetworks = socialNetworkDao.getAllSocialNetworks();
            for(SocialNetwork s:socialNetworks){

                result += "{ \"social_network_id:\" " + s.getSocialNetworkId()
                        + ", \"name\": " + s.getName()
                        +", \"client_id\": " + s.getClient().getClientId() +"}";
            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }
}