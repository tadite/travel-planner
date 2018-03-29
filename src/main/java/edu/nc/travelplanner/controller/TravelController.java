package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.config.AuthenticationFacade;
import edu.nc.travelplanner.dao.TravelDao;
import edu.nc.travelplanner.repository.ClientRepository;
import edu.nc.travelplanner.security.auth.JwtUser;
import edu.nc.travelplanner.service.travel.TravelService;
import edu.nc.travelplanner.table.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TravelController {

    @Autowired
    TravelService travelService;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping(path = "/api/client/travel")
    public ResponseEntity<List<TravelDao>> getAllTravelsByUser() {
        try {
            return new ResponseEntity(travelService
                    .getAllTravelsByUser((
                            getCurrentUser().getClientId())), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @GetMapping(path = "/api/client/travel/{pageNum}")
    public ResponseEntity<List<TravelDao>> getAllTravelsByPageAndUser(@PathVariable Integer pageNum) {
        try {
            return new ResponseEntity(travelService
                    .getAllTravelsByUserAndPage(getCurrentUser().getClientId(), pageNum), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @GetMapping(path = "/api/travel/{pageNum}")
    public ResponseEntity<List<TravelDao>> getAllTravelsByPage(@PathVariable Integer pageNum) {
        try {
            return new ResponseEntity(travelService
                    .getTravelsByPage(pageNum), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @GetMapping(path = "/api/travel")
    public ResponseEntity<List<TravelDao>> getAllTravels() {
        try {
            return new ResponseEntity(travelService
                    .getAllTravels(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @GetMapping(path = "/api/travel/{travelId}")
    public ResponseEntity<TravelDao> getTravelById(@PathVariable Long travelId) {
        try {
            return new ResponseEntity(travelService
                    .getTravelById(travelId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @DeleteMapping(path = "/api/travel/{travelId}")
    public ResponseEntity deleteTravelById(@PathVariable Long travelId) {
        try {
            travelService.deleteTravelById(travelId, getCurrentUser().getClientId());
            return new ResponseEntity(HttpStatus.OK);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }

    private String getCurrentUserName() {
        return ((Client) authenticationFacade
                .getAuthentication()
                .getPrincipal())
                .getLogin();
    }

    private Client getCurrentUser() {
        return (Client) authenticationFacade
                .getAuthentication()
                .getPrincipal();
    }
}
