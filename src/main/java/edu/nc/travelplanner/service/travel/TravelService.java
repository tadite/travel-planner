package edu.nc.travelplanner.service.travel;

import edu.nc.travelplanner.dao.ClientDao;
import edu.nc.travelplanner.dao.TravelDao;
import edu.nc.travelplanner.dto.afterPickTree.TravelDto;
import edu.nc.travelplanner.mapper.DtoMapper;
import edu.nc.travelplanner.repository.ClientRepository;
import edu.nc.travelplanner.repository.TravelRepository;
import edu.nc.travelplanner.table.Client;
import edu.nc.travelplanner.table.Roles;
import edu.nc.travelplanner.table.Travel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TravelService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    TravelRepository travelRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    DtoMapper dtoMapper;

    public List<TravelDto> getTravelsByPage(Integer page) {
        return travelRepository.findAll(new PageRequest(page, PAGE_SIZE))
                .map(travel -> dtoMapper.map(travel))
                .getContent();
    }

    public List<TravelDto> getAllTravels() {
        List<TravelDto> travelDtos = new LinkedList<>();
        travelRepository.findAll().iterator().forEachRemaining(travel -> travelDtos.add(dtoMapper.map(travel)));
        return travelDtos;
    }

    public Travel getTravelById(Long travelId) {
        return travelRepository.findOne(travelId);
    }

    public void deleteTravelById(Long travelId, Long clientId) {
        Client client = clientRepository.findOne(clientId);
        Travel travel = travelRepository.findOne(travelId);
        if (client.getRole()== Roles.ADMIN || travel.getClient().getClientId().equals(client.getClientId()))
            travelRepository.delete(travelId);

        throw new AccessDeniedException("You can't delete this travel.");
    }

    public List<TravelDto> getAllTravelsByUserAndPage(Long clientId, Integer page) {
        return travelRepository.findAllByClient_ClientId(new PageRequest(page, PAGE_SIZE), clientId)
                .map(travel -> dtoMapper.map(travel))
                .getContent();
    }

    public List<TravelDto> getAllTravelsByUser(Long clientId) {
        List<TravelDto> travelDtos = new LinkedList<>();
        travelRepository.findAllByClient_ClientId(clientId)
                .iterator()
                .forEachRemaining(travel -> travelDtos.add(dtoMapper.map(travel)));
        return travelDtos;
    }
}
