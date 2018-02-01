package edu.nc.travelplanner.modelController;

import java.util.List;

import edu.nc.travelplanner.dao.CityDao;
import edu.nc.travelplanner.dao.ClientDao;
import edu.nc.travelplanner.table.City;
import edu.nc.travelplanner.table.Client;
import edu.nc.travelplanner.table.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/client")
public class ClientController {
    @Autowired
    private ClientDao clientDao;

    @Autowired
    private CityDao cityDao;

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            Client client = new Client();
            client.setClientId(id);
            clientDao.delete(client);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Client succesfully deleted!";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String create(String firstName, String lastName, String email, Integer  age, String login, String password, Long city_id, String role, Boolean isBlocked){
        try {
            Client client = new Client();

            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setEmail(email);
            client.setAge(age);
            client.setLogin(login);
            client.setPassword(password);
            client.setRole(role);
            client.setIsBlocked(isBlocked);

            City city = cityDao.getCityById(city_id);

            if (city != null) {
                client.setCity(city);
                client.setCountry(city.getCountry());
            }

            clientDao.saveClient(client);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Client succesfully saved!";
    }
    @RequestMapping(value = "/allClients")
    @ResponseBody
    @Transactional
    public String getAllClients() {
        try {
            String result = "[";
            List<Client>  clients = clientDao.getAllClients();
            for(Client c:clients){

                result +=  "{ \"client_id:\" " + c.getClientId()
                        +  ", \"firstName\": " + c.getFirstName()
                        +  ", \"lastName\": " + c.getLastName()
                        +  ", \"email\": " + c.getEmail()
                        +  ", \"age\": " + c.getAge()
                        +  ", \"login\": " + c.getLogin()
                        +  ", \"password\": " + c.getPassword()
                        +  ", \"city_id\": " + c.getCity().getCityId()
                        + ", \"country_id\": " + c.getCountry().getCountryId()
                        +  ", \"role\": " + c.getRole()
                        +  ", \"isBlocked\": " + c.getIsBlocked()
                        + "},";

            }
            result += "]";
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }


}