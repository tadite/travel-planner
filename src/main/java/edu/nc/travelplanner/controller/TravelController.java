package edu.nc.travelplanner.controller;

import edu.nc.travelplanner.dao.TravelDao;
import edu.nc.travelplanner.dto.manage.UserManageDto;
import edu.nc.travelplanner.dto.travel.TravelPreviewDto;
import edu.nc.travelplanner.model.action.ActionArgs;
import edu.nc.travelplanner.model.action.ActionArgsBuilder;
import edu.nc.travelplanner.model.action.ActionState;
import edu.nc.travelplanner.service.travel.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/client/travel")
public class TravelController {

    @Autowired
    TravelService travelService;

    @GetMapping(path = "/preview/page/{pageNum}")
    public ResponseEntity<List<TravelPreviewDto>> getTravelPreviewsByPage(@PathVariable(value = "pageNum") int pageNum){
        return new ResponseEntity<>(travelService.getTravelsByPage(pageNum).stream().map(TravelPreviewDto::fromTravel).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/preview")
    public ResponseEntity<List<TravelPreviewDto>> getTravelPreviews(){
        return new ResponseEntity<>(travelService.getAllTravels().stream().map(TravelPreviewDto::fromTravel).collect(Collectors.toList()), HttpStatus.OK);
    }
}
